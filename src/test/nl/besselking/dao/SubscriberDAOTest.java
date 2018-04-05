package nl.besselking.dao;

import nl.besselking.domain.Subscriber;
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
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SubscriberDAOTest {

    @Mock
    DatabaseConnection db;

    @InjectMocks
    SubscriberDAO subscriberDAO;

    @Before
    public void setUp() throws SQLException, IOException {
        Connection con = TestMockDBConnection.getConnection();

        Mockito.when(db.getConn()).thenReturn(con);
    }

    @Test
    public void testGetAllSubscribers() {
        List<Subscriber> actual = subscriberDAO.list();
        List<Subscriber> expected = new ArrayList<>();
        expected.add(new Subscriber(1, "Marijn", "Besseling", "njirambem@gmail.com"));
        expected.add(new Subscriber(2, "Hendrik", "Camps", "wollaboi@yopmail.com"));

        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }
}