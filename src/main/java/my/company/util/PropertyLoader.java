package my.company.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Class that extracts properties from the prop file.
 */
public class PropertyLoader {

    private static final String APPLICATION_PROPERTIES = "/environment.properties";

    public static String loadProperty(String name) {
        return loadProperty(name, APPLICATION_PROPERTIES);
    }

    public static String loadProperty(String name, String fromResource) {
        Properties props = new Properties();
        try {
            props.load(PropertyLoader.class.getResourceAsStream(fromResource));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return props.getProperty(name);
    }
}
