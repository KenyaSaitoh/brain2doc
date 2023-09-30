package pro.kensait.brain2doc.config;

import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ConstMapHolder {
    @SuppressWarnings("rawtypes")
    private static Map map;
    static {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        Yaml yaml = new Yaml();
        map = yaml.loadAs(
                classloader.getResourceAsStream("const.yaml"), Map.class);
    }

    @SuppressWarnings("rawtypes")
    public static Map getConstMap() {
        return map;
    }
}