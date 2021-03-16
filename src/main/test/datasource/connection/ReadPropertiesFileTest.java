package datasource.connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadPropertiesFileTest {
    private ReadPropertiesFile sut;

    @BeforeEach
    void setSut() {
        sut = new ReadPropertiesFile();
    }

    @Test
    void getProperties() {
        var properties = sut.getProperties();
        assertEquals("test1234", properties.getProperty("TEST"));
    }
}
