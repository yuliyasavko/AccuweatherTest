package org.yuliya.accuweather;

import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class AbstractAccuweatherTest {

    private static Properties prop = new Properties();

    private static InputStream configFile;

    private static String apiKey;
    private static String apiKeyMinute;
    private static String baseUrl;

    @BeforeAll
    static void initConfig() throws IOException {
        configFile = Files.newInputStream(Paths.get("src/test/resources/accuweather.properties"));
        prop.load(configFile);

        apiKey = prop.getProperty("apiKey");
        apiKeyMinute = prop.getProperty("apiKeyMinute");
        baseUrl = prop.getProperty("baseUrl");

        configFile.close();
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getApiKeyMinute() {
        return apiKeyMinute;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

}
