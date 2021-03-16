package datasource.connection;

import com.mysql.jdbc.JDBC4Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;


class JDBCDatabaseConnectionTest {
    private JDBCDatabaseConnection sut;

    @BeforeEach
    void SetUp(){
        sut = new JDBCDatabaseConnection();
    }

    @Test
    void CreateConnectionTest() {
        try {
            var conn = sut.createConnection();
            assertTrue(conn instanceof JDBC4Connection);
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
