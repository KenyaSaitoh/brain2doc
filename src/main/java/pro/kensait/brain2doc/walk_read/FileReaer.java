package pro.kensait.brain2doc.walk_read;

import java.util.List;

public class FileReaer {
    


    private static String getLinesAsString(List<String> content) {
        StringBuilder sb = new StringBuilder();
        sb.append("日本語でお願いします Javaのソースコードです" + System.getProperty("line.separator"));
        for (String line : content) {
            sb.append(line + System.getProperty("line.separator"));
        }
        System.out.println(sb.toString());
        return sb.toString();
    }


}