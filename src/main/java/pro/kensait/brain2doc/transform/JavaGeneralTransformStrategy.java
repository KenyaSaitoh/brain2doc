package pro.kensait.brain2doc.transform;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pro.kensait.brain2doc.common.Const;

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
        String outputContent = Const.MARKDOWN_HEADING + 
                packageName + "." + "**" + className + "**" +
                (seqNum != 1 ? " [" + seqNum + "]" : "") +
                Const.LINE_SEP +
                Const.LINE_SEP +
                "ファイルパス: " + url +
                Const.LINE_SEP +
                Const.LINE_SEP +
                responseContent +
                Const.LINE_SEP +
                Const.LINE_SEP +
                Const.MARKDOWN_HORIZON + 
                Const.LINE_SEP;
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