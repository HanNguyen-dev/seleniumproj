package hellocucumber.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class EnvironmentUtil {

    private static EnvironmentUtil instance;
    private static Properties properties;
    private static final String baseConfigPath = "configs";

    private EnvironmentUtil() {}

    public static EnvironmentUtil getInstance() {
        if (instance == null) {
            instance = new EnvironmentUtil();
            String env = System.getProperty("env", "dev");
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(baseConfigPath + String.format("/%s.properties", env)));
                properties = new Properties();
                properties.load(reader);
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public String getEnvironment() {
        return properties.getProperty("ENV");
    }

    public String getSearchUrl() {
        return properties.getProperty("SEARCH_URL");
    }
}
