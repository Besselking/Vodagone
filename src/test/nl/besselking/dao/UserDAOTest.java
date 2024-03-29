package nl.besselking.dao;

import nl.besselking.domain.User;
import nl.besselking.service.DatabaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {


    @Mock
    private DatabaseConnection db;
    @InjectMocks
    private UserDAO userDAO;

    @Before
    public void init() throws SQLException, IOException{
        Connection con = TestMockDBConnection.getConnection();

        Mockito.when(db.getConn()).thenReturn(con);
    }

    @Test
    public void testIfCorrectUserIsFound() {
        User actual = userDAO.findByUserName("marijn");
        User expected = new User();
        expected.setId(1);
        expected.setUser("marijn");
        expected.setFirstName("Marijn");
        expected.setLastname("Besseling");
        expected.setPassword("$2a$12$zdi6bMOxBrv4wkD7.wgLsegDU9CSMZj7HLLk5xjjgTjiklsb7h0Fy");
        expected.setToken("1234-1234-1234");

        Assert.assertEquals(expected, actual);

    }
    @Test
    public void testIfNoUserIsFound() {
        User result = userDAO.findByUserName("asdf");
        Assert.assertNull(result);
    }
}
