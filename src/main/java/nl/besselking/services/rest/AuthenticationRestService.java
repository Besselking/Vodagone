package nl.besselking.services.rest;

import nl.besselking.dao.UserDAO;
import nl.besselking.domain.User;
import nl.besselking.dto.LoginRequest;
import nl.besselking.dto.LoginResponse;
import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.services.Password;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class AuthenticationRestService {

    @Inject
    private UserDAO udao;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(LoginRequest loginRequest) {
        try {
            User user = authenticate(loginRequest);

            if (user.getToken() == null) {
                user.issueToken();
                udao.addToken(user);
            }

            return Response.ok(new LoginResponse(user.getUser(), user.getToken())).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private User authenticate(LoginRequest loginRequest) throws UnauthorizedUserException {
        User storedUser;
        storedUser = udao.findByName(loginRequest.getUser());

        boolean passMatch = Password.checkPassword(loginRequest.getPassword(), storedUser.getPassword());

        if (!passMatch)
            throw new UnauthorizedUserException();

        return storedUser;

    }

}
