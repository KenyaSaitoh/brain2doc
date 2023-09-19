package pro.kensait.brain2doc.transform;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pro.kensait.brain2doc.common.Const;

public class OutputTransformer {
    private static final String PACKAGE_REGEX = "package (.*);";

    public static String transform(Path inputFilePath, String requestContent,
            List<String> responseContents) {

        List<String> responseLines = new ArrayList<>();
        for (String responseContent :responseContents) {
            for (String line : responseContent.split(Const.SEPARATOR)) {
                responseLines.add(line);
            }
        }
        String outputContent = "";
        for (String line : responseLines) {
            outputContent += line;
        }
        String packageName = getPackageName(requestContent);
        String className = inputFilePath.getFileName().toString()
                .replaceAll("(.*)\\.java", "$1");
        outputContent = "## " + 
                packageName + "." + className +
                Const.SEPARATOR +
                outputContent +
                Const.SEPARATOR +
                Const.SEPARATOR;
        return outputContent;
    }

    private static String getPackageName(String requestContent) {
        Matcher matcher = Pattern.compile(PACKAGE_REGEX)
                .matcher(requestContent);
        return matcher.find() ?
                matcher.group().replaceAll(PACKAGE_REGEX, "$1") :
                    "";
 
    }
}
