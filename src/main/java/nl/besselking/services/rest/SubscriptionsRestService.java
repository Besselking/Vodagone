package nl.besselking.services.rest;

import nl.besselking.dao.SubscriptionDAO;
import nl.besselking.dao.UserDAO;
import nl.besselking.domain.Subscription;
import nl.besselking.domain.User;
import nl.besselking.exceptions.UnauthorizedUserException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("abonnementen")
public class SubscriptionsRestService {

    @Inject
    private SubscriptionDAO subDAO;

    @Inject
    private UserDAO userDAO;

    @GET
    public Response getAllUserSubscriptions(@QueryParam("token") String token) {

        try {
            authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("all")
    public Response getAllSubscriptions(@QueryParam("token") String token,
                                        @QueryParam("filter") String filter){
        try {
            authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }


        List<Subscription> subs = subDAO.list(filter);

        return Response.ok(subs).build();
    }

    private User authToken(String token) throws UnauthorizedUserException {
        User storedUser = userDAO.findByToken(token);
        if (storedUser == null)
            throw new UnauthorizedUserException();
        return storedUser;
    }
}
