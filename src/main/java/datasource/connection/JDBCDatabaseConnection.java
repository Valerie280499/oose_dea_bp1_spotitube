package datasource.connection;

import com.mysql.jdbc.JDBC4Connection;

import javax.ejb.Singleton;
import javax.ws.rs.ServerErrorException;
import java.sql.*;

@Singleton
public class JDBCDatabaseConnection {
    private JDBC4Connection conn;

    public JDBC4Connection createConnection() {
        try{
            if (conn==null){
                var properties = new ReadPropertiesFile().getProperties();
                var connectionString = properties.getProperty("URL");
                conn = (JDBC4Connection) DriverManager.getConnection(connectionString);
            }
        } catch (SQLException throwables) {
            throw new ServerErrorException(500);
        }
        return conn;
    }
}
