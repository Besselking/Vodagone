package nl.besselking.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class HealthyRestController {

    @GET
    public Response healthy(){
        return Response.ok().build();
    }
}
