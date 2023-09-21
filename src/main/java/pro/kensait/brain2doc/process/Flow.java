package pro.kensait.brain2doc.process;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import pro.kensait.brain2doc.config.TemplateHolder;
import pro.kensait.brain2doc.openai.ApiClient;
import pro.kensait.brain2doc.params.Parameter;
import pro.kensait.brain2doc.params.ProcessType;
import pro.kensait.brain2doc.params.ResourceType;
import pro.kensait.brain2doc.transform.OutputTransformer;

public class Flow {
    private static Parameter param; // このクラス内のみで使われるグローバルな変数
    private static final String ZIP_FILE_EXT = ".zip";

    synchronized public static void init(Parameter paramValues) {
        param = paramValues;
    }

    public static void inputProcess() {
        if (Files.isDirectory(param.getSrcPath())) {
            walkDirectory(param.getSrcPath());
        } else {
            if (param.getSrcPath().getFileName().endsWith(ZIP_FILE_EXT)) {
                readZipFile(param.getSrcPath());
            } else {
                readNormalFile(param.getSrcPath());
            }
        }
    }

    private static void walkDirectory(Path srcPath) {
        try {
        Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path inputFilePath, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println("#####" + inputFilePath.getFileName() + "#####");
                if (Files.isDirectory(inputFilePath)) return FileVisitResult.CONTINUE;
                readNormalFile(inputFilePath);
                return FileVisitResult.CONTINUE;
            };
        });
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private static void readNormalFile(Path inputFilePath) {
        ResourceType resourceType = param.getResourceType();
        try {
            if (resourceType.matchesExt(inputFilePath.toString())) {
                System.out.println("###########" + inputFilePath.getFileName().toString());
                String ext = resourceType.getMatchExt(inputFilePath.toString());
                // 正規表現がパラメータとして指定されており、かつ、
                // 拡張子を取り除いたファイル名が、正規表現とマッチしなかった場合は、
                // 当該ファイルを対象外と見なし、直ちに処理を折り返す
                if (param.getSrcRegex() != null &&
                        ! extractNameWithoutExt(
                                inputFilePath.getFileName().toString(), ext)
                        .matches(param.getSrcRegex())) {
                    return;
                }
                List<String> inputFileLines = Files.readAllLines(inputFilePath);
                mainProcess(inputFilePath, inputFileLines);
            }
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private static void readZipFile(Path srcPath) {
        ResourceType resourceType = param.getResourceType();
        ZipInputStream zis = null;
        try {
            zis = new ZipInputStream(new FileInputStream(srcPath.toFile()));
            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                Path inputFilePath = Paths.get(srcPath.toString(), entryName);
                System.out.println("===== " + entryName + " =====");
                if (resourceType.matchesExt(entryName)) {
                    String ext = resourceType.getMatchExt(entryName.toString());
                    // 正規表現がパラメータとして指定されており、かつ、
                    // 拡張子を取り除いたファイル名が、正規表現とマッチしなかった場合は、
                    // 当該ファイルを対象外と見なし、直ちに処理を折り返す
                    if (param.getSrcRegex() != null &&
                            ! extractNameWithoutExt(entryName, ext)
                            .matches(param.getSrcRegex())) {
                        continue;
                    }
                    try (BufferedReader br =
                            new BufferedReader(new InputStreamReader(zis))) {
                        List<String> inputFileLines = new ArrayList<>();
                        String line;
                        while ((line = br.readLine()) != null) {
                            inputFileLines.add(line);
                        }
                        mainProcess(inputFilePath, inputFileLines);
                    }
                }
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            try {
                zis.close();
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }
    }

    private static void mainProcess(Path inputFilePath, List<String> inputFileLines) {
        List<String> requestLines = attachTemplate(inputFileLines);
        String requestContent = toReqString(requestLines);
        List<String> responseContents = ApiClient.ask(requestContent,
                param.getOpenaiURL(),
                param.getOpenaiModel(),
                param.getOpenaiApikey(),
                param.getProxyURL());
        String outputFileContent = OutputTransformer.transform(inputFilePath,
                requestContent, responseContents);
        write(outputFileContent);
    }

    private static void write(String responseContent) {
        try (FileWriter writer = new FileWriter(param.getDestFilePath().toString(),
                true)) {
           writer.append(responseContent);
           writer.flush();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @SuppressWarnings("rawtypes")
    private static List<String> attachTemplate(List<String> inputFileLines) {
        ResourceType resourceType = param.getResourceType();
        ProcessType processType = param.getProcessType();
        TemplateHolder th = TemplateHolder.getInstance();
        Map templateMap = th.getTemplateMap(param.getLocale(), param.getTemplateFile());

        Map resourceMap = (Map) templateMap.get(resourceType.getName());
        if (resourceMap == null || resourceMap.isEmpty())
            throw new IllegalArgumentException("テンプレートの誤り => リソース名の指定");
        
        Map processMap = (Map) resourceMap.get("processes");
        if (processMap == null || processMap.isEmpty())
            throw new IllegalArgumentException("テンプレートの誤り => プロセスの指定");
        
        String phrases = (String) processMap.get(processType.getName());
        if (processMap == null || processMap.isEmpty())
            throw new IllegalArgumentException("テンプレートの誤り => フレーズの指定");

        // TODO
        System.out.println(phrases);

        List<String> requestLines = new ArrayList<>();
        requestLines.add(phrases);
        requestLines.addAll(inputFileLines);
        return requestLines;
    }

    private static String extractNameWithoutExt(String fileName, String ext) {
        int lastExtIndex = fileName.lastIndexOf(ext);
        return fileName.substring(0, lastExtIndex);
    }

    private static String toReqString(List<String> inputFileLines) {
        String reqString = "";
        for (String line : inputFileLines) {
            reqString += line + System.getProperty("line.separator");
        }
        return reqString;
    }
}