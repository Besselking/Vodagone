package nl.besselking.rest;

import nl.besselking.dao.SubscriptionDAO;
import nl.besselking.dao.UserSubscriptionDAO;
import nl.besselking.domain.Subscription;
import nl.besselking.domain.User;
import nl.besselking.domain.UserSubscription;
import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.rest.dto.DetailedSubscriptionResponse;
import nl.besselking.rest.dto.SubscriptionListResponse;
import nl.besselking.rest.dto.SubscriptionRequest;
import nl.besselking.service.login.LoginService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("abonnementen")
public class SubscriptionsRestController {

    @Inject
    private SubscriptionDAO subDAO;

    @Inject
    private UserSubscriptionDAO usubDAO;

    @Inject
    private LoginService auth;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUserSubscriptions(@QueryParam("token") String token) {
        User storedUser;
        try {
            storedUser = auth.authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        SubscriptionListResponse subList = usubDAO.allUserSubscriptions(storedUser);


        return Response.ok(subList).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscription(@QueryParam("token") String token,
                                    @PathParam("id") int id) {
        User storedUser;
        try {
            storedUser = auth.authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        UserSubscription sub = usubDAO.getUserSubscription(storedUser, id);


        return Response.ok(new DetailedSubscriptionResponse(sub)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSubscription(@QueryParam("token") String token,
                                       @PathParam("id") int id) {
        User storedUser;
        try {
            storedUser = auth.authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        usubDAO.terminateSubscription(storedUser, id);


        return getSubscription(token, id);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewUserSubscription(@QueryParam("token") String token,
                                           SubscriptionRequest sub) {
        User storedUser;
        try {
            storedUser = auth.authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        usubDAO.addUserSubscription(storedUser, sub);

        return getAllUserSubscriptions(token);
    }

    @GET
    @Path("all")
    public Response getAllSubscriptions(@QueryParam("token") String token,
                                        @QueryParam("filter") String filter) {
        try {
            auth.authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }


        List<Subscription> subs = subDAO.list(filter);

        return Response.ok(subs).build();
    }

    @POST
    @Path("/{id}")
    public Response upgradeSubscription(@PathParam("id") int id,
                                        @QueryParam("token") String token) {
        try {
            auth.authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(403).build();
        }
        return Response.ok().build();
    }


}
