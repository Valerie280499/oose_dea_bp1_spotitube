package datasource.connection;

import javax.ws.rs.ServerErrorException;
import java.io.IOException;
import java.util.Properties;

class ReadPropertiesFile {
    private Properties properties;

    ReadPropertiesFile() {
        properties = new Properties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("db.properties"));
            Class.forName(properties.getProperty("driver"));
        } catch (IOException | ClassNotFoundException e) {
            throw new ServerErrorException(500);
        }
    }

    Properties getProperties() {
        return properties;
    }

}
