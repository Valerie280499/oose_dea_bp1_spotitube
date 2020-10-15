package datasource.dao;

import datasource.connection.JDBCConnection;
import datasource.dao.interfaces.IUserDAO;
import domain.User;
import domain.interfaces.IUser;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class UserDAO implements IUserDAO {
    private JDBCConnection JDBCConnection;

    @Inject
    public void setJDBCConnection(JDBCConnection JDBCConnection) {
        this.JDBCConnection = JDBCConnection;
    }

    @Override
    public Optional<IUser> getUser(String username) {
        try{
            var conn = JDBCConnection.createConnection();
            PreparedStatement statement =  conn.prepareStatement("SELECT * FROM user WHERE username = ?");
            statement.setString(1, username);
            ArrayList<IUser> foundUser = getUserFromDB(statement);
            if (foundUser.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(foundUser.get(0));
            }
        } catch(SQLException e){
            return Optional.empty();
        }
    }

    public ArrayList<IUser> getUserFromDB(PreparedStatement statement) throws SQLException{
        ArrayList<IUser> userList = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        User user = new User(resultSet.getInt("user_id"), resultSet.getString("username"), resultSet.getString("password"));
        userList.add(user);
        return userList;
    }


}
