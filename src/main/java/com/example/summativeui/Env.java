package com.example.summativeui;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Env {
    public static Map<String, String> readEnv() {
        Map<String, String> envMap = new HashMap<>();

        try (FileInputStream fis = new FileInputStream("C:\\Users\\44785\\Documents\\Winchester\\Year2\\BS2202\\Assignment\\JavaFX\\.env")) {
            Properties properties = new Properties();
            properties.load(fis);

            for (String key : properties.stringPropertyNames()) {
                envMap.put(key, properties.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return envMap;
    }
}
