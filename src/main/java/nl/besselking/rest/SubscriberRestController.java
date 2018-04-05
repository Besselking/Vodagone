package nl.besselking.rest;

import nl.besselking.domain.Subscriber;
import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.rest.dto.DTO;
import nl.besselking.rest.dto.impl.ShareRequest;
import nl.besselking.rest.dto.impl.SubscriberResponse;
import nl.besselking.service.subscription.SubscriberService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/abonnees")
public class SubscriberRestController extends RestController{

    @Inject
    private SubscriberService subscriberService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSubscribers(@QueryParam("token") String token){
        try {
            List<Subscriber> subscribers = subscriberService.getAllSubscribers(token);
            List<DTO> response = subscribers.stream().map(s -> new SubscriberResponse(
                    s.getId(),
                    s.getFirstname(),
                    s.getEmail()))
                    .collect(Collectors.toList());
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
