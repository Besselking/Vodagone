package nl.besselking.rest;

import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.rest.dto.LoginRequest;
import nl.besselking.service.login.LoginService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginRestController loginRestController;
    private LoginRequest rq;

    @Before
    public void setup() {
        rq = new LoginRequest();
    }

    @Test
    public void emptyRequestReturnsStat403() throws UnauthorizedUserException {
        Mockito.when(loginService.authenticate(null, null)).thenThrow(UnauthorizedUserException.class);

        Response resp = loginRestController.login(rq);

        Assert.assertThat(resp.getStatus(), is(403));
    }

    @Test
    public void wrongUserRequestReturnsStat403() throws UnauthorizedUserException {
        rq.setUser("asdf");
        rq.setPassword("sdfa");

        Mockito.when(loginService.authenticate(rq.getUser(), rq.getPassword())).thenThrow(UnauthorizedUserException.class);

        Response resp = loginRestController.login(rq);

        Assert.assertThat(resp.getStatus(), is(403));
    }
}
