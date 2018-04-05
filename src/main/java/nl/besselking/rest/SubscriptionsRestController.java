package nl.besselking.rest;

import nl.besselking.domain.Subscription;
import nl.besselking.domain.UserSubscription;
import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.rest.dto.DTO;
import nl.besselking.rest.dto.impl.*;
import nl.besselking.service.subscription.SubscriptionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("abonnementen")
public class SubscriptionsRestController extends RestController {

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
            UserSubscription userSubscription = subscriptionService.getSubscription(token, id);
            DetailedSubscriptionResponse response = new DetailedSubscriptionResponse(
                    userSubscription.getId(),
                    userSubscription.getAanbieder(),
                    userSubscription.getDienst(),
                    userSubscription.getPrijs(),
                    userSubscription.getStartDatum(),
                    userSubscription.getVerdubbeling(),
                    userSubscription.getDeelbaar(),
                    userSubscription.getStatus());
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
            SubscriptionListResponse response = subscriptionService.getAllUserSubscriptions(token);
            return respondCreated(response);
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
            List<Subscription> subscriptions = subscriptionService.getAllSubscriptions(token, filter);
            List<DTO> response = subscriptions.stream().map(s -> new SubscriptionResponse(
                    s.getId(),
                    s.getAanbieder(),
                    s.getDienst()))
                    .collect(Collectors.toList());
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
            UserSubscription userSubscription = subscriptionService.getSubscription(token, id);
            DetailedSubscriptionResponse response = new DetailedSubscriptionResponse(
                    userSubscription.getId(),
                    userSubscription.getAanbieder(),
                    userSubscription.getDienst(),
                    userSubscription.getPrijs(),
                    userSubscription.getStartDatum(),
                    userSubscription.getVerdubbeling(),
                    userSubscription.getDeelbaar(),
                    userSubscription.getStatus());
            return respondCreated(response);
        } catch (UnauthorizedUserException e) {
            return respondUnauthorized(e);
        }
    }
}
