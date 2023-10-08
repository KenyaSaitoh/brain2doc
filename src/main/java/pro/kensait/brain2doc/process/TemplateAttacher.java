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
    @SuppressWarnings("rawtypes")
    public static Prompt attach(List<String> inputFileLines,
            ResourceType resourceType,
            GenerateType generateType,
            String genTable,
            String fields,
            OutputScaleType outputSizeType,
            Locale locale,
            Path templateFile,
            int count) {

        TemplateHolder th = TemplateHolder.getInstance();
        Map templateMap = th.getTemplateMap(locale, templateFile);
        Map messageMap = (Map) templateMap.get("message");
        Map resourceMap = (Map) templateMap.get(resourceType.getName());
        if (resourceMap == null || resourceMap.isEmpty())
            throw new IllegalArgumentException("テンプレート（リソース）の記述誤り");

        // systemメッセージの構築
        String systemStr = (String) messageMap.get("system") + LINE_SEP;

        // assistantメッセージの構築
        String continuing = count != 0 ?
                (String) messageMap.get("continuing") + LINE_SEP : "";
        String assistantMessage =  (String) (messageMap.get("constraints")) + 
                continuing + LINE_SEP +
                (String) (messageMap.get("lang")) + LINE_SEP +
                getScaleString(messageMap, outputSizeType) +
                (String) (messageMap.get("markdown-level") + LINE_SEP);

        // userメッセージの構築
        String titleStr = (String) messageMap.get("title");
        String templateStr = null;
        if (generateType != null) {
            templateStr = (String) resourceMap.get(generateType.getName());
            if (templateStr == null) {
                templateStr = (String) messageMap.get("default-gen");
            }
        } else {
            templateStr = getTableTemplateStr(messageMap, genTable, fields) + LINE_SEP;
        }
        String userMessage = titleStr + LINE_SEP + templateStr; 

        List<String> userMessageLines = new ArrayList<>();
        userMessageLines.add(userMessage);
        userMessageLines.add(LINE_SEP + (String) (messageMap.get("input")) + LINE_SEP);
        userMessageLines.addAll(inputFileLines);
        return new Prompt(systemStr, assistantMessage, userMessage, userMessageLines);
    }

    /*
     * 出力内容の大きさを指定するための文字列を取得する
     */
    @SuppressWarnings("rawtypes")
    private static String getScaleString(Map messageMap, OutputScaleType outputSizeType) {
        switch (outputSizeType) {
        case SMALL:
            return (String) (messageMap.get("char-limit-prefix")) +
                    OutputScaleType.SMALL.getCharSize() +
                    (String) (messageMap.get("char-limit-suffix")) +
                    LINE_SEP;
        case MEDIUM:
            return (String) (messageMap.get("char-limit-prefix")) +
                    OutputScaleType.MEDIUM.getCharSize() +
                    (String) (messageMap.get("char-limit-suffix")) +
                    LINE_SEP;
        case LARGE:
            return (String) (messageMap.get("char-limit-prefix")) +
                    OutputScaleType.LARGE.getCharSize() +
                    (String) (messageMap.get("char-limit-suffix")) +
                    LINE_SEP;
        default:
            return "";
        }
    }

    /*
     * 一覧形式のテンプレートを返す
     */
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

    /*
     * プロンプトを表すクラス
     */
    public static class Prompt {
        private final String systemMessage;
        private final String assistantMessage;
        private final String userMessage;
        private final List<String> userMessageLines;
        public Prompt(String systemMessage, String assistantMessage, String userMessage,
                List<String> userMessageLines) {
            this.systemMessage = systemMessage;
            this.assistantMessage = assistantMessage;
            this.userMessage = userMessage;
            this.userMessageLines = userMessageLines;
        }
        public String getSystemMessage() {
            return systemMessage;
        }
        public String getAssistantMessage() {
            return assistantMessage;
        }
        public String getUserMessage() {
            return userMessage;
        }
        public List<String> getUserMessageLines() {
            return userMessageLines;
        }
    }
}