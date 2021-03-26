package datasource.connection;

import com.mysql.jdbc.JDBC4Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class JDBCDatabaseConnectionTest {
    private JDBCDatabaseConnection sut;

    @BeforeEach
    void SetUp(){
        sut = new JDBCDatabaseConnection();
    }

    @Test
    void CreateConnectionTest() {
        var conn = sut.createConnection();
        assertTrue(conn instanceof JDBC4Connection);
    }
}
