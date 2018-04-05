package nl.besselking.rest;

import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.rest.dto.impl.SubscriptionListResponse;
import nl.besselking.service.subscription.SubscriptionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionRestControllerTest {

    @Mock
    SubscriptionService subscriptionService;

    @InjectMocks
    private SubscriptionsRestController subscriptionsRestController;

    @Before
    public void setup() {

    }

    @Test
    public void testGetAllUserSubscriptions() throws UnauthorizedUserException {
        Mockito.when(subscriptionService.getAllUserSubscriptions(anyString()))
                .thenReturn(new SubscriptionListResponse(new ArrayList<>(), 1));
        Response response = subscriptionsRestController.getAllUserSubscriptions("testToken");
        Assert.assertEquals(200, response.getStatus());
    }

}
