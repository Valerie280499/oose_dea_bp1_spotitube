package datasource.dao;

import datasource.connection.JDBCConnection;
import datasource.errors.TokenNotFoundError;
import datasource.errors.UserNotFoundError;
import datasource.errors.SomeSQLError;
import datasource.resultsetMappers.MapResultsetToUserDTO;
import dto.UserDTO;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    public static final String QUERY = "SELECT * FROM login WHERE username = ?";
    public static final String USERNAME_FROM_LOGIN_WHERE_TOKEN = "SELECT username FROM login WHERE token = ?";
    public static final String UPDATE_LOGIN_SET_TOKEN_WHERE_USERNAME = "UPDATE LOGIN SET token = ? WHERE username = ?";
    private JDBCConnection JDBCConnection;
    private MapResultsetToUserDTO mapResultsetToUserDTO;

    @Inject public void setJDBCConnection(JDBCConnection JDBCConnection) {
        this.JDBCConnection = JDBCConnection;
    }

    @Inject public void setMapResultsetToUserDTO(MapResultsetToUserDTO mapResultsetToUserDTO){this.mapResultsetToUserDTO = mapResultsetToUserDTO;}

    public UserDTO getUser(String username) {
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

        } catch(SQLException error){
            throw new SomeSQLError(error);
        }
    }

    public void updateTokenForUser(String username, String token) {

        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(UPDATE_LOGIN_SET_TOKEN_WHERE_USERNAME);

            statement.setString(1, token);
            statement.setString(2, username);
            statement.execute();

        } catch (SQLException error){
            throw new SomeSQLError(error);
        }
    }

    public void getUserByToken(String token) {

        try {
            var conn = JDBCConnection.createConnection();
            var statement = conn.prepareStatement(USERNAME_FROM_LOGIN_WHERE_TOKEN);

            statement.setString(1, token);
            var foundUser = executeQuery(statement);

            if (foundUser.isEmpty()) {
                throw new TokenNotFoundError();
            }

        } catch (SQLException error){
            throw new SomeSQLError(error);
        }
    }

    public ArrayList<UserDTO> executeQuery(PreparedStatement statement) throws SQLException{
        var userList = new ArrayList<UserDTO>();
        var resultSet = statement.executeQuery();
        resultSet.next();
        userList.add(mapResultsetToUserDTO.map(resultSet));
        return userList;
    }
}
