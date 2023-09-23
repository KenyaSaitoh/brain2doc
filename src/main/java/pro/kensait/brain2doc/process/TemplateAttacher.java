package pro.kensait.brain2doc.process;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pro.kensait.brain2doc.config.TemplateHolder;
import pro.kensait.brain2doc.params.OutputScaleType;
import pro.kensait.brain2doc.params.OutputType;
import pro.kensait.brain2doc.params.ResourceType;

public class TemplateAttacher {
    private static final String OUTPUT_SCALE_PREFIX = "回答は";
    private static final String OUTPUT_SCALE_SUFFIX = "文字以内でお願いします。";
    private static final String MARKDOWN_LEVEL_MESSAGE = "Markdownの見出しは、レベル4以上にしてください。";
    private static final String LANG_MESSAGE = "回答は日本語でお願いします。";

    private static final String OUTPUT_SCALE_PREFIX_EN = "Please keep the answer within ";
    private static final String OUTPUT_SCALE_SUFFIX_EN = " words.";
    private static final String MARKDOWN_LEVEL_MESSAGE_EN = "Please set the headings in Markdown to level 4 or higher.";
    private static final String LANG_MESSAGE_EN = "Please answer in English.";

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

        List<String> requestLines = new ArrayList<>();
        requestLines.add(templateStr);
        requestLines.add(getLangMessage(locale));
        requestLines.add(getScaleString(locale, outputSizeType));
        requestLines.add(getMarkdownLevelString(locale));

        System.out.println(requestLines);
        requestLines.addAll(inputFileLines);
        return requestLines;
    }

    private static String getLangMessage(Locale locale) {
        if (locale.getLanguage().equals("en")) {
            return LANG_MESSAGE_EN;
        }
        return LANG_MESSAGE;
    }

    private static String getMarkdownLevelString(Locale locale) {
        if (locale.getLanguage().equals("en")) {
            return MARKDOWN_LEVEL_MESSAGE_EN;
        }
        return MARKDOWN_LEVEL_MESSAGE;
    }

    private static String getScaleString(Locale locale, OutputScaleType outputSizeType) {
        if (locale.getLanguage().equals("en")) {
            switch (outputSizeType) {
            case SMALL:
                return OUTPUT_SCALE_PREFIX_EN + OutputScaleType.SMALL.getCharSize()
                        + OUTPUT_SCALE_SUFFIX_EN;
            case MEDIUM:
                return OUTPUT_SCALE_PREFIX_EN + OutputScaleType.MEDIUM.getCharSize()
                        + OUTPUT_SCALE_SUFFIX_EN;
            case LARGE:
                return OUTPUT_SCALE_PREFIX_EN + OutputScaleType.LARGE.getCharSize()
                        + OUTPUT_SCALE_SUFFIX_EN;
            default:
                return "";
            }
        } else {
            switch (outputSizeType) {
            case SMALL:
                return OUTPUT_SCALE_PREFIX + OutputScaleType.SMALL.getCharSize()
                        + OUTPUT_SCALE_SUFFIX;
            case MEDIUM:
                return OUTPUT_SCALE_PREFIX + OutputScaleType.MEDIUM.getCharSize()
                        + OUTPUT_SCALE_SUFFIX;
            case LARGE:
                return OUTPUT_SCALE_PREFIX + OutputScaleType.LARGE.getCharSize()
                        + OUTPUT_SCALE_SUFFIX;
            default:
                return "";
            }
        }
    }
}