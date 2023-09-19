package pro.kensait.brain2doc.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConstValueHolder {
    private static Properties props;
    static {
        props = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classloader.getResourceAsStream("const.properties")) {
            props.load(new InputStreamReader(is, StandardCharsets.UTF_8));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}