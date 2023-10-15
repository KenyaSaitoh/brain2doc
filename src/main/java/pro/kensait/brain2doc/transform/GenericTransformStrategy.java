package pro.kensait.brain2doc.transform;

import static pro.kensait.brain2doc.common.Const.*;

import java.nio.file.Path;
import java.util.List;

public class GenericTransformStrategy implements TransformStrategy {
    @Override
    public String transform(Path inputFilePath, String requestContent,
            List<String> responseLines, int seqNum) {
        String responseContent = "";
        for (String line : responseLines) {
            responseContent += line;
        }
        String fileName = inputFilePath.getFileName().toString();
        String url = inputFilePath.toUri().toString();

        String outputContent = MARKDOWN_HEADING + 
                BOLD + fileName + BOLD +
                (seqNum != 1 ? " [" + seqNum + "]" : "") +
                LINE_SEP +
                LINE_SEP +
                FILE_URL_TITLE + url +
                LINE_SEP +
                LINE_SEP +
                responseContent +
                LINE_SEP +
                LINE_SEP +
                MARKDOWN_HORIZON + 
                LINE_SEP;
        return outputContent;
    }
}