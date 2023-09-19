package pro.kensait.brain2doc.config;

import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import pro.kensait.brain2doc.params.Parameter;

public class PromptHolder {
    private static final String PROMPT_YAML_FILE_PREFIX = "prompt_";
    private static final String PROMPT_YAML_FILE_EXT = ".yaml";

    @SuppressWarnings("rawtypes")
    private static Map promptMap; 

    @SuppressWarnings("rawtypes")
    synchronized public static Map getMap() {
        if (promptMap == null) {
            Parameter param = Parameter.getParameter();
            String yamlFileName = PROMPT_YAML_FILE_PREFIX +
                    param.getLocale().getLanguage() + PROMPT_YAML_FILE_EXT;
            System.out.println("####" + yamlFileName);
            Yaml yaml = new Yaml();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            promptMap = yaml.loadAs(classloader.getResourceAsStream(yamlFileName),
                    Map.class);
        }
        return promptMap;
    }
}