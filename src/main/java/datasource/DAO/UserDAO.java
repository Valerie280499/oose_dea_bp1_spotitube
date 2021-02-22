package datasource.DAO;

import datasource.connection.JDBCConnection;
import datasource.DAO.interfaces.IUserDAO;
import datasource.errors.UserNotFoundError;
import datasource.errors.someSQLError;
import dto.UserDTO;
import dto.interfaces.IUserDTO;

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
    public IUserDTO getUser(String username) {
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

    public ArrayList<IUserDTO> executeQuery(PreparedStatement statement) throws SQLException{
        var userList = new ArrayList<IUserDTO>();
        var resultSet = statement.executeQuery();
        resultSet.next();
        var user = new UserDTO(resultSet.getString("username"), resultSet.getString("password"), resultSet.getByte("token"));
        userList.add(user);
        return userList;
    }


}
