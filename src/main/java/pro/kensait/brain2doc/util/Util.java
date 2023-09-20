package pro.kensait.brain2doc.util;

public class Util {

    public static String extractNameWithoutExt(String fileName, String ext) {
        int lastExtIndex = fileName.lastIndexOf(ext);
        return fileName.substring(0, lastExtIndex);
    }
}