package nl.besselking.rest;

import nl.besselking.exceptions.UnauthorizedUserException;

import javax.ws.rs.core.Response;

abstract class RestController {
    Response respondOk(Object response) {
        return Response.ok(response).build();
    }

    Response respondUnauthorized(UnauthorizedUserException e) {
        return Response.status(403).entity(e).build();
    }

    protected Response respondOk() {
        return Response.ok().build();
    }
}
