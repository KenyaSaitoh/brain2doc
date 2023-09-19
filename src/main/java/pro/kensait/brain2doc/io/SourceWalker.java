package pro.kensait.brain2doc.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import pro.kensait.brain2doc.apiclient.ApiClient;
import pro.kensait.brain2doc.params.Parameter;

public class SourceWalker {
    
    private static void walkDirectory(Parameter param) throws IOException {
        Files.walkFileTree(param.getSrcPath(), new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path inputFile, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println("#####" + inputFile.getFileName() + "#####");
                if (Files.isDirectory(inputFile)) return FileVisitResult.CONTINUE;
                if (inputFile.toString().endsWith(".java")) {
                    List<String> requestLines = Files.readAllLines(inputFile);
                    ApiClient.askToOpenAI(param, requestLines);
                }
                return FileVisitResult.CONTINUE;
            };
        });
    }

    private static String getLinesAsString(List<String> content) {
        StringBuilder sb = new StringBuilder();
        sb.append("日本語でお願いします Javaのソースコードです" + System.getProperty("line.separator"));
        for (String line : content) {
            sb.append(line + System.getProperty("line.separator"));
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    private static void processZipFile(Parameter param) {
        Path srcPath = param.getSrcPath();
        ZipInputStream zis = null;
        try {
            zis = new ZipInputStream(new FileInputStream(srcPath.toFile()));
            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                System.out.println("===== " + entryName + " =====");
                if (param.getResourceType().matchesExt(entryName)) {
                    try (BufferedReader br =
                            new BufferedReader(new InputStreamReader(zis))) {
                        List<String> requestLines = new ArrayList<>();
                        String line;
                        while ((line = br.readLine()) != null) {
                            requestLines.add(line);
                        }
                        ApiClient.askToOpenAI(param, requestLines);
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
}