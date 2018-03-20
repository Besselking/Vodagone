package nl.besselking.rest;

import nl.besselking.rest.dto.LoginRequest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class LoginTest {

    @Test
    public void emptyRequestReturnsStat403() {
        LoginRequest rq = new LoginRequest();

        LoginRestController endpoint = new LoginRestController();

        Response resp = endpoint.authenticateUser(rq);

        Assert.assertEquals(403, resp.getStatus());
    }
}
