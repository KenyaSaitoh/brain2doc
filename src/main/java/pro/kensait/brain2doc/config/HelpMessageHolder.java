package pro.kensait.brain2doc.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
 * ヘルプメッセージを所定のテキストファイルから読み込んで、言語種別ごとに保持するクラス
 */
public class HelpMessageHolder {
    private static final String HELP_FILE_PREFIX = "help_";
    private static final String HELP_FILE_EXT = ".txt";

    private static final HelpMessageHolder helpHolder = new HelpMessageHolder();

    public static HelpMessageHolder getInstance() {
        return helpHolder;
    }

    private List<String> helpMessage;

    private HelpMessageHolder() {
    }

    synchronized public List<String> getHelpMessage(String lang) {
        if (helpMessage == null) {
            List<String> result = new ArrayList<>();
            String templateFileName = HELP_FILE_PREFIX + lang + HELP_FILE_EXT;
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            try (InputStream is = classloader.getResourceAsStream(templateFileName);
                 InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(isr)) {
                String line;
                while ((line = br.readLine()) != null) {
                    result.add(line);
                }
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
            helpMessage = result;
        }
        return helpMessage;
    }
}