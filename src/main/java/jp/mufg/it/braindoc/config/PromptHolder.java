package jp.mufg.it.braindoc.config;

import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class PromptHolder {
    @SuppressWarnings("rawtypes")
    private static Map map; 

    static {
        Yaml yaml = new Yaml();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        map = yaml.loadAs(classloader.getResourceAsStream("prompt_jp.yaml"), Map.class);
    }

    @SuppressWarnings("rawtypes")
    public static Map getMap() {
        return map;
    }
}