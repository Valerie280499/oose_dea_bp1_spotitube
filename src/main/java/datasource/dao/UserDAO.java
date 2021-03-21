package datasource.dao;

import com.mysql.jdbc.JDBC4Connection;
import datasource.connection.JDBCDatabaseConnection;
import datasource.errors.TokenNotFoundError;
import datasource.errors.UserNotFoundError;
import datasource.resultsetMappers.MapResultsetToUserDTO;
import dto.UserDTO;

import javax.inject.Inject;
import javax.ws.rs.ServerErrorException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    public static final String SELECT_FROM_LOGIN_WHERE_USERNAME = "SELECT * FROM login WHERE username = ?";
    public static final String USERNAME_FROM_LOGIN_WHERE_TOKEN = "SELECT * FROM login WHERE token = ?";
    public static final String UPDATE_LOGIN_SET_TOKEN_WHERE_USERNAME = "UPDATE LOGIN SET token = ? WHERE username = ?";
    private JDBC4Connection JDBCconnection;
    private MapResultsetToUserDTO mapResultsetToUserDTO;

    @Inject public void setJDBCConnection(JDBCDatabaseConnection JDBCDatabaseConnection) {
        this.JDBCconnection = JDBCDatabaseConnection.createConnection();
    }

    @Inject public void setMapResultsetToUserDTO(MapResultsetToUserDTO mapResultsetToUserDTO){this.mapResultsetToUserDTO = mapResultsetToUserDTO;}

    public UserDTO getUser(String username) {

        try{
            var statement =  JDBCconnection.prepareStatement(SELECT_FROM_LOGIN_WHERE_USERNAME);

            statement.setString(1, username);
            var foundUser = executeQuery(statement);

            if (foundUser.isEmpty()) {
                throw new UserNotFoundError();
            } else {
                return foundUser.get(0);
            }

        } catch(SQLException error){
            throw new ServerErrorException(500);
        }
    }

    public void updateTokenForUser(String username, String token) {

        try {
            var statement = JDBCconnection.prepareStatement(UPDATE_LOGIN_SET_TOKEN_WHERE_USERNAME);

            statement.setString(1, token);
            statement.setString(2, username);
            statement.execute();

        } catch (SQLException error){
            error.printStackTrace();
            throw new ServerErrorException(500);
        }
    }

    public void getUserByToken(String token) {

        try {
            var statement = JDBCconnection.prepareStatement(USERNAME_FROM_LOGIN_WHERE_TOKEN);

            statement.setString(1, token);
            var foundUser = executeQuery(statement);

            if (foundUser.isEmpty()) {
                throw new TokenNotFoundError();
            }

        } catch (SQLException error){
            throw new ServerErrorException(500);
        }
    }

    public ArrayList<UserDTO> executeQuery(PreparedStatement statement) throws SQLException{
        var userList = new ArrayList<UserDTO>();
        var resultSet = statement.executeQuery();

        if(resultSet.next()){
            var userDTO = mapResultsetToUserDTO.map(resultSet);
            userList.add(userDTO);
        }
        return userList;
    }
}
