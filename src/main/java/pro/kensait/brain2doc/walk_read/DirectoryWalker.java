package pro.kensait.brain2doc.walk_read;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import pro.kensait.brain2doc.openai.ApiClient;

public class DirectoryWalker {
    
    public static void walkDirectory(Path srcPath) throws IOException {
        Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path inputFile, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println("#####" + inputFile.getFileName() + "#####");
                if (Files.isDirectory(inputFile)) return FileVisitResult.CONTINUE;
                if (inputFile.toString().endsWith(".java")) {
                    List<String> requestLines = Files.readAllLines(inputFile);
                    ApiClient.askToOpenAI(requestLines);
                }
                return FileVisitResult.CONTINUE;
            };
        });
    }
}