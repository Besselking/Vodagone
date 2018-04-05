package nl.besselking.rest;

import nl.besselking.domain.User;
import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.rest.dto.impl.LoginRequest;
import nl.besselking.rest.dto.impl.LoginResponse;
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

@RunWith(MockitoJUnitRunner.class)
public class LoginRestControllerTest {

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

        loginRestController.login(rq);
    }

    @Test
    public void wrongUserRequestThrowsUnauthorizedUserException() throws UnauthorizedUserException {
        rq.setUser("asdf");
        rq.setPassword("sdfa");

        Mockito.when(loginService.authenticate("asdf", "sdfa")).thenThrow(UnauthorizedUserException.class);

        Response actual = loginRestController.login(rq);
        Assert.assertEquals(403, actual.getStatus());
    }

    @Test
    public void correctUserRequest() throws UnauthorizedUserException {
        rq.setUser("marijn");
        rq.setPassword("test123");

        LoginResponse lr = new LoginResponse("Marijn", "Besseling", "1234-1234-1234");
        User user = new User();
        user.setId(1);
        user.setFirstName("Marijn");
        user.setLastname("Besseling");
        user.setToken("1234-1234-1234");
        user.setPassword("radfadf");
        user.setUser("marijn");

        Mockito.when(loginService.authenticate("marijn", "test123")).thenReturn(user);

        Response actual =  loginRestController.login(rq);

        Assert.assertEquals(actual.getStatus(), Response.ok(lr).build().getStatus());
    }
}
