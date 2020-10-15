package datasource.connection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadPropertiesFileTest {
    private ReadPropertiesFile sut;

    @Test
    void getProperties() {
        sut = new ReadPropertiesFile();
        var properties = sut.getProperties();

        Assertions.assertEquals("test1234", properties.getProperty("TEST"));
    }
}