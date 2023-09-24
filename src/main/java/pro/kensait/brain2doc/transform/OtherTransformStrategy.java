package pro.kensait.brain2doc.transform;

import java.nio.file.Path;
import java.util.List;

import pro.kensait.brain2doc.common.Const;

public class OtherTransformStrategy implements TransformStrategy {
    @Override
    public String transform(Path inputFilePath, String requestContent,
            List<String> responseLines, int seqNum) {
        String responseContent = "";
        for (String line : responseLines) {
            responseContent += line;
        }
        String fileName = inputFilePath.getFileName().toString();
        String url = inputFilePath.toUri().toString();
        String outputContent = Const.MARKDOWN_HEADING + 
                "**" + fileName + "**" +
                (seqNum != 1 ? " [" + seqNum + "]" : "") +
                Const.SEPARATOR +
                Const.SEPARATOR +
                "ファイルリンク: " + url +
                Const.SEPARATOR +
                Const.SEPARATOR +
                responseContent +
                Const.SEPARATOR +
                Const.SEPARATOR +
                Const.MARKDOWN_HORIZON + 
                Const.SEPARATOR;
        return outputContent;
    }
}