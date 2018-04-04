package nl.besselking.rest;

import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.rest.dto.ShareRequest;
import nl.besselking.service.subscription.SubscriberService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/abonnees")
public class SubscriberRestController extends RestController{

    @Inject
    private SubscriberService subscriberService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSubscribers(@QueryParam("token") String token){
        try {
            List response = subscriberService.getAllSubscribers(token);
            return respondOk(response);
        } catch (UnauthorizedUserException e) {
            return respondUnauthorized(e);
        }
    }

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response shareSubscription(@QueryParam("token") String token,
                                      @PathParam("id") int id,
                                      ShareRequest request){
        try {
            subscriberService.shareSubscription(token, id, request.getId());
        } catch (UnauthorizedUserException e) {
            return respondUnauthorized(e);
        }
        return respondCreated();
    }

}
