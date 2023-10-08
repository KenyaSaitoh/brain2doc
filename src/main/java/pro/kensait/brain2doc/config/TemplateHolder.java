package pro.kensait.brain2doc.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/*
 * プロンプトのテンプレートを保持するクラス
 */
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
            String templateFileName = TEMPLATE_FILE_PREFIX + locale.getLanguage() +
                    TEMPLATE_FILE_EXT;
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            templateMap = yaml.loadAs(
                    classloader.getResourceAsStream(templateFileName), Map.class);
            if (templateFile != null) {
                // 外部ファイルの指定がある場合
                try (InputStream is = Files.newInputStream(templateFile)) {
                    Map outerTemplateMap = yaml.loadAs(is, Map.class);
                    // マージする
                    templateMap = merge(templateMap, outerTemplateMap);
                } catch (IOException ioe) {
                    throw new IllegalArgumentException("テンプレートファイルに誤りがあります");
                }
            }
        }
        return templateMap;
    }

    /*
     * 組み込みのテンプレートファイルと、オプションとして指定された外部のテンプレートファイルを、
     * Mapとしてマージする（外部ファイルの記述内容が優先）
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static Map merge(Map source, Map target) {
        for (Object key : target.keySet()) {
            if (source.get(key) instanceof Map && target.get(key) instanceof Map) {
                merge((Map) source.get(key), (Map) target.get(key));
            } else {
                source.put(key, target.get(key));
            }
        }
        return source;
    }
}