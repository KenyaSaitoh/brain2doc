package pro.kensait.brain2doc.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class TemplateHolder {
    private static final String TEMPLATE_FILE_PREFIX = "template_";
    private static final String TEMPLATE_FILE_EXT = ".yaml";

    private static final TemplateHolder templateHolder = new TemplateHolder(); 

    public static TemplateHolder getInstance() {
        return templateHolder;
    }
    
    @SuppressWarnings("rawtypes")
    private Map templateMap; 

    private TemplateHolder() {}

    @SuppressWarnings("rawtypes")
    synchronized public Map getTemplateMap(Locale locale, Path templateFile) {
        if (templateMap == null) {
            Yaml yaml = new Yaml();
            if (templateFile == null) {
                String templateFileName = TEMPLATE_FILE_PREFIX + locale.getLanguage() +
                        TEMPLATE_FILE_EXT;
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                templateMap = yaml.loadAs(
                        classloader.getResourceAsStream(templateFileName), Map.class);
            } else {
                try (InputStream is = Files.newInputStream(templateFile)) {
                    templateMap = yaml.loadAs(is, Map.class);
                } catch (IOException ioe) {
                    throw new IllegalArgumentException("テンプレートファイルの指定に誤りがあります");
                }
            }
        }
        return templateMap;
    }
}