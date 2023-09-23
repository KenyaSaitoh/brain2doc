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
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import pro.kensait.brain2doc.common.Const;
import pro.kensait.brain2doc.exception.OpenAIClientException;
import pro.kensait.brain2doc.exception.OpenAIRetryCountOverException;
import pro.kensait.brain2doc.openai.ApiClient;
import pro.kensait.brain2doc.openai.ApiResult;
import pro.kensait.brain2doc.openai.SuccessResponseBody;
import pro.kensait.brain2doc.params.OutputType;
import pro.kensait.brain2doc.params.Parameter;
import pro.kensait.brain2doc.params.ResourceType;
import pro.kensait.brain2doc.transform.JavaGeneralTransformStrategy;
import pro.kensait.brain2doc.transform.TransformStrategy;

public class Flow {
    private static final String ZIP_FILE_EXT = ".zip";
    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private static final String TIMEOUT_MESSAGE = "TIMEOUT";
    private static final String CONTEXT_LENGTH_EXCEEDED_CODE = "context_length_exceeded";
    private static final String TOKEN_LIMIT_OVER_MESSAGE = "TOKEN_LIMIT_OVER";
    private static final String CLIENT_ERROR_MESSAGE = "CLIENT_ERROR";
    
    private static Parameter param; // このクラス内のみで使われるグローバルな変数
    private static List<String> reportList = new CopyOnWriteArrayList<String>(); 

    synchronized public static void init(Parameter paramValues) {
        param = paramValues;
        reportList.clear();
    }

    public static List<String> getReportList() {
        return reportList;
    }

    public static void startAndFork() {
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
        List<String> requestLines = TemplateAttacher.attach(inputFileLines,
                param.getResourceType(),
                param.getOutputType(),
                param.getOutputScaleType(),
                param.getLocale(),
                param.getTemplateFile());
        String requestContent = toReqString(requestLines);
        ApiResult apiResult = null;
        try {
            apiResult = askToOpenAi(requestContent);
        } catch(OpenAIClientException oe) {
            System.out.println(oe.getClientErrorBody());
            if (Objects.equals(CONTEXT_LENGTH_EXCEEDED_CODE,
                    oe.getClientErrorBody().getError().getCode())) {
                addReport(inputFilePath, TOKEN_LIMIT_OVER_MESSAGE, 0);
                return; // 次のファイルへ
            }
            addReport(inputFilePath, CLIENT_ERROR_MESSAGE, 0);
            throw oe;
        } catch(OpenAIRetryCountOverException oe) {
            addReport(inputFilePath, TIMEOUT_MESSAGE, 0);
            throw oe;
        }
        List<String> responseChoices = toChoicesFromResponce(apiResult.getResponseBody());
        List<String> responseLines = toLineListFromChoices(responseChoices);
        TransformStrategy transStrategy = getOutputStrategy(param.getResourceType(),
                param.getOutputType());
        String outputFileContent = transStrategy.transform(inputFilePath,
                requestContent, responseLines);
        write(outputFileContent);
        addReport(inputFilePath, SUCCESS_MESSAGE, apiResult.getInterval());
    }

    private static ApiResult askToOpenAi(String requestContent) {
        ApiResult result = ApiClient.ask(requestContent,
                param.getOpenaiURL(),
                param.getOpenaiModel(),
                param.getOpenaiApikey(),
                param.getProxyURL(),
                param.getConnectTimeout(),
                param.getRequestTimeout(),
                param.getRetryCount(),
                param.getRetryInterval());
        return result;
    }

    private static String extractNameWithoutExt(String fileName, String ext) {
        int lastExtIndex = fileName.lastIndexOf(ext);
        return fileName.substring(0, lastExtIndex);
    }

    private static String toReqString(List<String> inputFileLines) {
        String reqString = "";
        for (String line : inputFileLines) {
            reqString += line + Const.SEPARATOR;
        }
        return reqString;
    }

    private static List<String> toChoicesFromResponce(SuccessResponseBody responseBody) {
        List<String> responseChoiceList = new ArrayList<>();
        responseBody.getChoices().forEach(choice -> {
            String responseContent = choice.getMessage().getContent();
            responseChoiceList.add(responseContent);
        });
        return responseChoiceList;
    }
    
    // 複数のChoiceがリストで返されるので、それを文字列リストに変換する
    private static List<String> toLineListFromChoices(
            List<String> responseChoices) {
        List<String> responseLines = new ArrayList<>();
        for (String responseChoice :responseChoices) {
            for (String line : responseChoice.split(Const.SEPARATOR)) {
                responseLines.add(line);
            }
        }
        return responseLines;
    }

    // TODO
    private static TransformStrategy getOutputStrategy(ResourceType resourceType,
            OutputType outputType) {
        if (resourceType == ResourceType.JAVA) {
            if (outputType == OutputType.SPEC ||
                    outputType == OutputType.REFACTORING) {
                return new JavaGeneralTransformStrategy();
            }
        }
        return null;
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

    private static void addReport(Path inputFilePath, String message, long interval) {
        String report = inputFilePath.getFileName().toString() + "," +
                message + "," + interval;
        reportList.add(report);
    }
}