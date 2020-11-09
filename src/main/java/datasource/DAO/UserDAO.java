package datasource.DAO;

import datasource.connection.JDBCConnection;
import datasource.DAO.interfaces.IUserDAO;
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
            var statement =  conn.prepareStatement("SELECT * FROM login WHERE username = ?");
            statement.setString(1, username);
            var foundUser = getUserFromDB(statement);
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
        var userList = new ArrayList<IUser>();
        var resultSet = statement.executeQuery();
        resultSet.next();
        var user = new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getByte("token"));
        userList.add(user);
        return userList;
    }


}
