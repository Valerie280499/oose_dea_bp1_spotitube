package datasource.DAO;

import com.mysql.jdbc.JDBC4Connection;
import datasource.connection.JDBCConnection;
import domain.interfaces.IUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDAOTest {
    private JDBCConnection jdbcConnection;
    private JDBCConnection fakeJDBCConnection;
    private JDBC4Connection fakeConn;
    private PreparedStatement fakeStatement;
    private UserDAO sut;

    @BeforeEach
    void setUp(){
        try {
            jdbcConnection = new JDBCConnection();
            fakeConn = jdbcConnection.createConnection();
            fakeStatement =  fakeConn.prepareStatement("SELECT * FROM login WHERE username = 'Valerie'");
        } catch (SQLException e) { e.printStackTrace(); }

        try {
            fakeJDBCConnection = mock(JDBCConnection.class);
            when(fakeJDBCConnection.createConnection()).thenReturn(fakeConn);
        } catch (SQLException e) { e.printStackTrace(); }

        sut = new UserDAO();
    }

    @Test
    void getExistingUserFromDatabaseTest() {
        sut.setJDBCConnection(fakeJDBCConnection);

        var foundUser = sut.getUser("Valerie");

        Assertions.assertEquals("Valerie", foundUser.get().getUsername());
    }

    @Test
    void getNonexistingUserFromDatabaseTest(){
        sut.setJDBCConnection(fakeJDBCConnection);

        var foundUser = sut.getUser("fakePerson");

        Assertions.assertEquals(Optional.empty(), foundUser);
    }

    @Test
    void getUserFromDBTest(){
        ArrayList<IUser> userList = new ArrayList<>();
        sut.setJDBCConnection(fakeJDBCConnection);

        try {
            userList = sut.getUserFromDB(fakeStatement);
        } catch (SQLException e) { e.printStackTrace(); }

        Assertions.assertEquals("Valerie", userList.get(0).getUsername());

    }

}
