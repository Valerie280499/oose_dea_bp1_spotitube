package datasource.connection;

import com.mysql.jdbc.JDBC4Connection;

import java.sql.*;
import java.util.Properties;

public class JDBCConnection {
    private JDBC4Connection conn;
    private Properties properties;

    public JDBC4Connection createConnection() throws SQLException {
        properties = new ReadPropertiesFile().getProperties();
        var connectionString = properties.getProperty("URL");
        conn = (JDBC4Connection) DriverManager.getConnection(connectionString);
        return conn;
    }
}
