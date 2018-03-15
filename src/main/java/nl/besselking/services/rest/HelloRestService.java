package nl.besselking.services.rest;

import nl.besselking.services.Password;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("hello")
@Produces("text/plain")
public class HelloRestService {

    @GET
    public Response hello() {
        String pw = Password.hashPassword("test123");

        return Response.ok(pw).build();
    }
}
