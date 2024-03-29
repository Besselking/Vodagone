package nl.besselking.rest;

import nl.besselking.domain.User;
import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.rest.dto.impl.LoginRequest;
import nl.besselking.rest.dto.impl.LoginResponse;
import nl.besselking.service.login.LoginService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class LoginRestController extends RestController{

    @Inject
    private LoginService loginService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        try {
            User user = loginService.authenticate(loginRequest.getUser(), loginRequest.getPassword());
            LoginResponse response = new LoginResponse(user.getFirstName(), user.getLastname(), user.getToken());
            return respondOk(response);

        } catch (UnauthorizedUserException e) {
            return respondUnauthorized(e);
        }
    }

}
