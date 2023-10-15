package pro.kensait.brain2doc.transform;

import static pro.kensait.brain2doc.common.Const.*;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGeneralTransformStrategy implements TransformStrategy {
    private static final String PACKAGE_REGEX = "package (.*);";

    @Override
    public String transform(Path inputFilePath, String requestContent,
            List<String> responseLines, int seqNum) {
        String responseContent = "";
        for (String line : responseLines) {
            responseContent += line;
        }
        String packageName = getPackageName(requestContent);
        String className = inputFilePath.getFileName().toString()
                .replaceAll("(.*)\\.java", "$1");
        String url = inputFilePath.toUri().toString();
        String outputContent = MARKDOWN_HEADING + 
                packageName + "." + BOLD + className + BOLD +
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

    private String getPackageName(String requestContent) {
        Matcher matcher = Pattern.compile(PACKAGE_REGEX)
                .matcher(requestContent);
        return matcher.find() ?
                matcher.group().replaceAll(PACKAGE_REGEX, "$1") :
                    "";
    }
}