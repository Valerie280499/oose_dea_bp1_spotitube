package datasource.resultsetMappers;

import dto.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapResultsetToUserDTO {

    public UserDTO map(ResultSet resultSet) throws SQLException {
        var user = new UserDTO();
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setToken(resultSet.getString("token"));
        return user;
    }
}
