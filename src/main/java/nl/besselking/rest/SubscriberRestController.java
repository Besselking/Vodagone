package nl.besselking.rest;

import nl.besselking.rest.dto.ShareRequest;
import nl.besselking.service.subscription.SubscriberService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/abonnees")
public class SubscriberRestController {

    @Inject
    private SubscriberService subscriberService;

    @GET
    public Response getAllSubscribers(@QueryParam("token") String token){
        return subscriberService.getAllSubscribers(token);
    }

    @POST
    @Path("{id}")
    public Response shareSubscription(@QueryParam("token") String token,
                                      @PathParam("id") int id,
                                      ShareRequest request){
        subscriberService.shareSubscription(token, id, request.getId());
        return Response.ok().build();

    }
}
