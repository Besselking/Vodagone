package nl.besselking.service.subscription;

import javax.ws.rs.core.Response;

public class SubscriberService {
    public Response getAllSubscribers(String token) {
        return Response.ok().build();
    }
}
