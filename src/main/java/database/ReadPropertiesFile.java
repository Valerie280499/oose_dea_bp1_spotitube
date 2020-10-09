package database;

import java.io.IOException;
import java.util.Properties;

public class ReadPropertiesFile {
    private Properties properties;

    public ReadPropertiesFile() {
        properties = new Properties();
        properties.getProperty("name_db");

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("db.properties"));
            Class.forName(properties.getProperty("driver"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }

}
