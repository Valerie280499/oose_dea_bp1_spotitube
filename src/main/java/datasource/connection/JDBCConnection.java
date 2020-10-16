package datasource.connection;

import com.mysql.jdbc.JDBC4Connection;

import java.sql.*;
import java.util.Properties;

public class JDBCConnection {

    public JDBC4Connection createConnection() throws SQLException {
        Properties properties = new ReadPropertiesFile().getProperties();
        var connectionString = properties.getProperty("URL");
        JDBC4Connection conn = (JDBC4Connection) DriverManager.getConnection(connectionString);
        return conn;
    }
}
