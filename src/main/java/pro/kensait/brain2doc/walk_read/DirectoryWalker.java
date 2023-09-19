package pro.kensait.brain2doc.walk_read;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import pro.kensait.brain2doc.params.ResourceType;

public class DirectoryWalker {
    
    public static void walkDirectory(Path srcPath, ResourceType resourceType) {
        try {
        Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() { 
            @Override
            public FileVisitResult visitFile(Path inputFilePath, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println("#####" + inputFilePath.getFileName() + "#####");
                if (Files.isDirectory(inputFilePath)) return FileVisitResult.CONTINUE;
                FileProcessor.processFile(inputFilePath, resourceType);
                return FileVisitResult.CONTINUE;
            };
        });
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}