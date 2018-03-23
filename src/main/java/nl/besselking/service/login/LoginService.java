package nl.besselking.service.login;

import nl.besselking.domain.User;
import nl.besselking.exceptions.UnauthorizedUserException;

public interface LoginService {
    User authenticate(String username, String password) throws UnauthorizedUserException;

    User authToken(String token) throws UnauthorizedUserException;
}
