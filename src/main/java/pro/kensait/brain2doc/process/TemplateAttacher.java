package pro.kensait.brain2doc.process;

import static pro.kensait.brain2doc.common.Const.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pro.kensait.brain2doc.config.TemplateHolder;
import pro.kensait.brain2doc.params.GenerateType;
import pro.kensait.brain2doc.params.OutputScaleType;
import pro.kensait.brain2doc.params.ResourceType;

public class TemplateAttacher {
    private static final String PROMPT_HEADING = "### プロンプト入力内容（除くソース）";

    @SuppressWarnings("rawtypes")
    public static List<String> attach(List<String> inputFileLines,
            ResourceType resourceType,
            GenerateType generateType,
            String genTable,
            String fields,
            OutputScaleType outputSizeType,
            Locale locale,
            Path templateFile,
            boolean printPrompt,
            int count) {

        TemplateHolder th = TemplateHolder.getInstance();
        Map templateMap = th.getTemplateMap(locale, templateFile);
        Map messageMap = (Map) templateMap.get("message");
        Map resourceMap = (Map) templateMap.get(resourceType.getName());
        if (resourceMap == null || resourceMap.isEmpty())
            throw new IllegalArgumentException("テンプレート（リソース）の記述誤り");

        String commonStr = (String) messageMap.get("common");
        String templateStr = null;
        if (generateType != null) {
            templateStr = (String) resourceMap.get(generateType.getName());
            if (templateStr == null) {
                templateStr = (String) messageMap.get("default-gen");
            }
        } else {
            templateStr = getTableTemplateStr(messageMap, genTable, fields);
        }

        String continuing = count != 0 ?
                (String) messageMap.get("continuing") + LINE_SEP : "";
        String promptMessage = commonStr + LINE_SEP +
                templateStr + LINE_SEP +
                continuing + LINE_SEP +
                (String) (messageMap.get("constraints")) + LINE_SEP +
                (String) (messageMap.get("lang")) + LINE_SEP +
                getScaleString(messageMap, outputSizeType) + LINE_SEP +
                (String) (messageMap.get("markdown-level") + LINE_SEP);

        if (printPrompt) {
            printPrompt(promptMessage);
        }

        List<String> requestLines = new ArrayList<>();
        requestLines.add(promptMessage);
        requestLines.add(LINE_SEP + (String) (messageMap.get("input")) + LINE_SEP);
        requestLines.addAll(inputFileLines);
        return requestLines;
    }

    @SuppressWarnings("rawtypes")
    private static String getScaleString(Map messageMap, OutputScaleType outputSizeType) {
        switch (outputSizeType) {
        case SMALL:
            return (String) (messageMap.get("char-limit-prefix")) +
                    OutputScaleType.SMALL.getCharSize() +
                    (String) (messageMap.get("char-limit-suffix"));
        case MEDIUM:
            return (String) (messageMap.get("char-limit-prefix")) +
                    OutputScaleType.MEDIUM.getCharSize() +
                    (String) (messageMap.get("char-limit-suffix"));
        case LARGE:
            return (String) (messageMap.get("char-limit-prefix")) +
                    OutputScaleType.LARGE.getCharSize() +
                    (String) (messageMap.get("char-limit-suffix"));
        default:
            return "";
        }
    }

    @SuppressWarnings("rawtypes")
    private static String getTableTemplateStr(Map messageMap, String genTable,
            String fields) {
        String str1 = (String) (messageMap.get("table-prefix")) +
                genTable +
                (String) (messageMap.get("table-suffix")) + LINE_SEP;
        String str2 = (String) (messageMap.get("fields-prefix")) + "[" +
                fields + "]" +
                (String) (messageMap.get("fields-suffix"));
        return str1 + str2;
    }

    private static void printPrompt(String templateStr) {
        System.out.println(LINE_SEP + LINE_SEP +
                PROMPT_HEADING);
        System.out.print(templateStr);
        System.out.println("");
    }
}