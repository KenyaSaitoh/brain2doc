package jp.mufg.it.braindoc.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class DefaultPropsHolder {
    private static Properties props;
    static {
        props = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classloader.getResourceAsStream("default.properties")) {
            props.load(new InputStreamReader(is, StandardCharsets.UTF_8));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}