package nl.besselking.dao;

import nl.besselking.domain.Subscription;
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
public class SubscriptionDAOTest {

    @Mock
    private DatabaseConnection db;
    @InjectMocks
    private SubscriptionDAO subscriptionDAO;

    @Before
    public void init() throws SQLException, IOException {
        Connection con = TestMockDBConnection.getConnection();
        Mockito.when(db.getConn()).thenReturn(con);
    }

    @Test
    public void testGetAllSubscriptions() {
        List<Subscription> actual = subscriptionDAO.list(0, "");
        List<Subscription> expected = new ArrayList<>();
        expected.add(new Subscription(2, "vodafone", "Mobiele telefonie 100"));
        expected.add(new Subscription(3, "vodafone", "Mobiele telefonie 250"));
        expected.add(new Subscription(4, "vodafone", "Glasvezel-internet (download 500 Mbps)"));
        expected.add(new Subscription(5, "ziggo", "Kabel-internet (download 300 Mbps)"));
        expected.add(new Subscription(6, "ziggo", "Eredivisie Live 1.2.3.4 en 5"));
        expected.add(new Subscription(7, "ziggo", "HBO Plus"));

        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }
}
