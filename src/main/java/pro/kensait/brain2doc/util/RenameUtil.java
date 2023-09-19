package pro.kensait.brain2doc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import pro.kensait.brain2doc.common.Const;

public class RenameUtil {

    public static void renameIfExists(Path destPath) {
        if (! Files.exists(destPath)) return; 
        String fileName = destPath.getFileName().toString();
        String newFileName = extractNameWithoutExt(fileName) + "-" +
                getCurrentDateTimeStr() + Const.OUTPUT_FILE_EXT;
        Path renamePath = Paths.get(destPath.getParent().toString(), newFileName);
        try {
            Files.move(destPath, renamePath);
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    private static String extractNameWithoutExt(String fileName) {
        int lastExtIndex = fileName.lastIndexOf(Const.OUTPUT_FILE_EXT);
        return fileName.substring(0, lastExtIndex);
    }

    private static String getCurrentDateTimeStr() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yMMddHHmmss");
        return localDateTime.format(dateTimeFormatter);
    }
}