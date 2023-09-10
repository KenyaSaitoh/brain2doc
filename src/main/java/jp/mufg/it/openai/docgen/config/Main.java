package jp.mufg.it.openai.docgen.config;

import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class Main {
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        Yaml yaml = new Yaml();

        Map map = yaml.loadAs(ClassLoader.getSystemResourceAsStream("prompt_jp.yaml"), Map.class);

        Map map2 = (Map) map.get("shellscript");
        System.out.println(map2.get("ext"));

        Map map3 = (Map) map2.get("types");
        String explanationMessage = (String) map3.get("explanation");
        System.out.println(explanationMessage);

    }
}
