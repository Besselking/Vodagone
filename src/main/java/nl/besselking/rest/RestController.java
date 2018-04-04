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

    Response respondOk() {
        return Response.ok().build();
    }

    Response respondCreated(Object response) {
        return Response.status(201).entity(response).build();
    }

    protected Response respondCreated() {
        return Response.status(201).build();
    }
}
