package nl.besselking.service.login;

import nl.besselking.dao.UserDAO;
import nl.besselking.domain.User;
import nl.besselking.exceptions.UnauthorizedUserException;

import javax.inject.Inject;

public class LoginService {

    public LoginService() {
    }

    @Inject
    UserDAO userDAO;

    public User authenticate(String username, String password) throws UnauthorizedUserException {
        User storedUser;
        storedUser = userDAO.findByName(username);

        boolean passMatch = PasswordHashService.checkPassword(password, storedUser.getPassword());

        if (!passMatch)
            throw new UnauthorizedUserException();

        if (storedUser.getToken() == null) {
            storedUser.issueToken();
            userDAO.addToken(storedUser);
        }

        return storedUser;

    }
    public User authToken(String token) throws UnauthorizedUserException {
        User storedUser = userDAO.findByToken(token);
        if (storedUser == null)
            throw new UnauthorizedUserException();
        return storedUser;
    }
}
