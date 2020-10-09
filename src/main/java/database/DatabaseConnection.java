package database;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DatabaseConnection {

    private Connection conn;
    private Properties properties;
    private ResultSet resultSet;

    public DatabaseConnection() {
        properties = new ReadPropertiesFile().getProperties();
    }

    public void connectToDatabase(String query){
        try {
            createConnection();
            executeQuery(query);
            getResultsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createConnection() throws SQLException {
        conn = (Connection) DriverManager.getConnection(
                properties.getProperty("URL"),
                properties.getProperty("user"),
                properties.getProperty("password"));
    }

    void executeQuery(String query) throws SQLException {
        Statement statement = conn.createStatement();
        resultSet = statement.executeQuery(query);
    }

    void getResultsFromDatabase() throws SQLException {
        HashMap<Integer, List<String>> dictionary = new HashMap<Integer, List<String>>();
        List<String> user = new ArrayList<>();

        while(resultSet.next()){
            user.add(resultSet.getString(2));
            user.add(resultSet.getString(3));
            dictionary.put(resultSet.getInt(1), user);
        }
        conn.close();
        System.out.println("printjesssss");
        System.out.println(dictionary);
        System.out.println(dictionary.get(1));
        //return dictionary;

//            resultSet.getInt(1)
//            resultSet.getString(2)
//            resultSet.getString(3)
//            conn.close();

//        while(resultSet.next())
//            System.out.println(resultSet.getInt(1)+"  "+resultSet.getString(2)+"  "+resultSet.getString(3));
//        conn.close();
    }
}
