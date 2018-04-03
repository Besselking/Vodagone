package nl.besselking.rest;

import nl.besselking.domain.Subscription;
import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.rest.dto.DetailedSubscriptionResponse;
import nl.besselking.rest.dto.SubscriptionListResponse;
import nl.besselking.rest.dto.SubscriptionRequest;
import nl.besselking.rest.dto.UpgradeRequest;
import nl.besselking.service.subscription.SubscriptionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("abonnementen")
public class SubscriptionsRestController extends RestController{

    @Inject
    SubscriptionService subscriptionService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUserSubscriptions(@QueryParam("token") String token) {
        try {
            SubscriptionListResponse response = subscriptionService.getAllUserSubscriptions(token);
            return respondOk(response);
        } catch (UnauthorizedUserException e) {
            return respondUnauthorized(e);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscription(@QueryParam("token") String token,
                                    @PathParam("id") int id) {
        try {
            DetailedSubscriptionResponse response = subscriptionService.getSubscription(token, id);
            return respondOk(response);
        } catch (UnauthorizedUserException e) {
            return respondUnauthorized(e);
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSubscription(@QueryParam("token") String token,
                                       @PathParam("id") int id) {
        try {
            subscriptionService.terminate(token, id);
            return getSubscription(token, id);
        } catch (UnauthorizedUserException e) {
            return respondUnauthorized(e);
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewUserSubscription(@QueryParam("token") String token,
                                           SubscriptionRequest sub) {
        try {
            subscriptionService.addNewUserSubscription(token, sub.getId());
            return getSubscription(token, sub.getId());
        } catch (UnauthorizedUserException e) {
            return respondUnauthorized(e);
        }
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSubscriptions(@QueryParam("token") String token,
                                        @QueryParam("filter") String filter) {
        try {
            List<Subscription> response = subscriptionService.getAllSubscriptions(token, filter);
            return respondOk(response);
        } catch (UnauthorizedUserException e) {
            return respondUnauthorized(e);
        }
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upgradeSubscription(@QueryParam("token") String token,
                                        @PathParam("id") int id,
                                        UpgradeRequest upgradeRequest) {
        try {
            subscriptionService.upgradeSubscription(token, id, upgradeRequest.getVerdubbeling());
            return getSubscription(token, id);
        } catch (UnauthorizedUserException e) {
            return respondUnauthorized(e);
        }
    }
}
