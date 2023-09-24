package pro.kensait.brain2doc.process;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pro.kensait.brain2doc.common.Const;
import pro.kensait.brain2doc.config.TemplateHolder;
import pro.kensait.brain2doc.params.OutputScaleType;
import pro.kensait.brain2doc.params.OutputType;
import pro.kensait.brain2doc.params.ResourceType;

public class TemplateAttacher {

    @SuppressWarnings("rawtypes")
    public static List<String> attach(List<String> inputFileLines,
            ResourceType resourceType,
            OutputType outputType,
            OutputScaleType outputSizeType,
            Locale locale,
            Path templateFile) {
        TemplateHolder th = TemplateHolder.getInstance();
        Map templateMap = th.getTemplateMap(locale, templateFile);

        Map resourceMap = (Map) templateMap.get(resourceType.getName());
        if (resourceMap == null || resourceMap.isEmpty())
            throw new IllegalArgumentException("テンプレート（リソース）の誤り");

        String templateStr = (String) resourceMap.get(outputType.getName());
        if (templateStr == null || templateStr.isEmpty())
            throw new IllegalArgumentException("テンプレートの誤り");

        Map messageMap = (Map) templateMap.get("message");
        List<String> requestLines = new ArrayList<>();
        requestLines.add(templateStr);
        requestLines.add((String) (messageMap.get("constraints")));
        requestLines.add((String) (messageMap.get("lang")));
        requestLines.add(getScaleString(messageMap, outputSizeType));
        requestLines.add((String) (messageMap.get("markdown-level")));
        requestLines.add(Const.SEPARATOR +
                (String) (messageMap.get("input")) +
                Const.SEPARATOR);

        System.out.println(requestLines);
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
}