package nl.besselking.rest;

import nl.besselking.service.subscription.SubscriberService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

public class SubscriberRestController {

    @Inject
    private SubscriberService subscriberService;

    @GET
    @Path("/abonnees")
    public Response getAllSubscribers(@QueryParam("token") String token){
        return subscriberService.getAllSubscribers(token);
    }
}
