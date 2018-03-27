package nl.besselking.rest;

import nl.besselking.rest.dto.SubscriptionRequest;
import nl.besselking.rest.dto.UpgradeRequest;
import nl.besselking.service.subscription.SubscriptionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("abonnementen")
public class SubscriptionsRestController {

    @Inject
    SubscriptionService subscriptionService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUserSubscriptions(@QueryParam("token") String token) {
        return subscriptionService.getAllUserSubscriptions(token);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscription(@QueryParam("token") String token,
                                    @PathParam("id") int id) {
        return subscriptionService.getSubscription(token, id);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSubscription(@QueryParam("token") String token,
                                       @PathParam("id") int id) {
        return subscriptionService.terminate(token, id);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewUserSubscription(@QueryParam("token") String token,
                                           SubscriptionRequest sub) {
        return subscriptionService.addNewUserSubscription(token, sub.getId());
    }

    @GET
    @Path("all")
    public Response getAllSubscriptions(@QueryParam("token") String token,
                                        @QueryParam("filter") String filter) {
        return subscriptionService.getAllSubscriptions(token, filter);
    }

    @POST
    @Path("/{id}")
    public Response upgradeSubscription(@QueryParam("token") String token,
                                        @PathParam("id") int id,
                                        UpgradeRequest upgradeRequest) {
        return subscriptionService.upgradeSubscription(token, id, upgradeRequest.getVerdubbeling());
    }


}
