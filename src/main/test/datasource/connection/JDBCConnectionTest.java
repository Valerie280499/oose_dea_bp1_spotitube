package datasource.connection;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class JDBCConnectionTest {
    private JDBCConnection sut;

    @Test
    void createConnection() {
        sut = new JDBCConnection();

        try {
            var conn = sut.createConnection();
            System.out.println(conn);
        } catch (SQLException e) { e.printStackTrace(); }
    }
}