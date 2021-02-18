package datasource.DAO;

import datasource.connection.JDBCConnection;
import datasource.DAO.interfaces.IUserDAO;
import datasource.errors.UserNotFoundError;
import datasource.errors.someSQLError;
import domain.User;
import domain.interfaces.IUser;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO implements IUserDAO {
    public static final String QUERY = "SELECT * FROM login WHERE username = ?";
    private JDBCConnection JDBCConnection;

    @Inject
    public void setJDBCConnection(JDBCConnection JDBCConnection) {
        this.JDBCConnection = JDBCConnection;
    }

    @Override
    public IUser getUser(String username) {
        try{
            var conn = JDBCConnection.createConnection();
            var statement =  conn.prepareStatement(QUERY);

            statement.setString(1, username);
            var foundUser = executeQuery(statement);

            if (foundUser.isEmpty()) {
                throw new UserNotFoundError();
            } else {
                return foundUser.get(0);
            }

        } catch(SQLException e){
            throw new someSQLError();
        }
    }

    public ArrayList<IUser> executeQuery(PreparedStatement statement) throws SQLException{
        var userList = new ArrayList<IUser>();
        var resultSet = statement.executeQuery();
        resultSet.next();
        var user = new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getByte("token"));
        userList.add(user);
        return userList;
    }


}
