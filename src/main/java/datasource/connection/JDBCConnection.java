package datasource.connection;

import com.mysql.jdbc.JDBC4Connection;

import javax.ejb.Singleton;
import java.sql.*;

@Singleton
public class JDBCConnection {
    private JDBC4Connection conn;

    public JDBC4Connection createConnection() throws SQLException {
        if (conn==null){
            var properties = new ReadPropertiesFile().getProperties();
            var connectionString = properties.getProperty("URL");
            conn = (JDBC4Connection) DriverManager.getConnection(connectionString);
        }
        return conn;
    }
}
