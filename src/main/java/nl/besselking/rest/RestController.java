package nl.besselking.rest;

import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.rest.dto.DTO;

import javax.ws.rs.core.Response;
import java.util.List;

abstract class RestController {
    Response respondOk(DTO response) {
        return Response.ok(response).build();
    }
    Response respondOk(List<DTO> response) {
        return Response.ok(response).build();
    }

    Response respondUnauthorized(UnauthorizedUserException e) {
        return Response.status(403).entity(e).build();
    }

    Response respondOk() {
        return Response.ok().build();
    }

    Response respondCreated(DTO response) {
        return Response.status(201).entity(response).build();
    }
    Response respondCreated(List<DTO> response) {
        return Response.status(201).entity(response).build();
    }

    Response respondCreated() {
        return Response.status(201).build();
    }
}
