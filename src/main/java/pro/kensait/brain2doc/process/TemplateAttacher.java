package pro.kensait.brain2doc.process;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pro.kensait.brain2doc.common.Const;
import pro.kensait.brain2doc.config.TemplateHolder;
import pro.kensait.brain2doc.params.GenerateType;
import pro.kensait.brain2doc.params.OutputScaleType;
import pro.kensait.brain2doc.params.ResourceType;

public class TemplateAttacher {

    @SuppressWarnings("rawtypes")
    public static List<String> attach(List<String> inputFileLines,
            ResourceType resourceType,
            GenerateType generateType,
            String genListName,
            String[] fieldNames,
            OutputScaleType outputSizeType,
            Locale locale,
            Path templateFile) {
        TemplateHolder th = TemplateHolder.getInstance();
        Map templateMap = th.getTemplateMap(locale, templateFile);
        Map messageMap = (Map) templateMap.get("message");
        Map resourceMap = (Map) templateMap.get(resourceType.getName());
        if (resourceMap == null || resourceMap.isEmpty())
            throw new IllegalArgumentException("テンプレート（リソース）の誤り");

        String templateStr = (String) messageMap.get("common");
        String added = null;
        if (generateType != null) {
            added = (String) resourceMap.get(generateType.getName());
            if (added == null) {
                throw new IllegalArgumentException("テンプレートの誤り");
            }
        } else {
            added = getListTemplateStr(messageMap, genListName, fieldNames);
        }
        templateStr += added;

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

    private static String getListTemplateStr(Map messageMap, String genListName,
            String[] fieldNames) {
        String str1 = (String) (messageMap.get("list-prefix")) +
                genListName +
                (String) (messageMap.get("list-suffix"));
        String str2 = (String) (messageMap.get("fields-prefix")) + "[" +
                fieldNames + "]" +
                (String) (messageMap.get("fields-suffix"));
        return str1 + str2;
    }
}