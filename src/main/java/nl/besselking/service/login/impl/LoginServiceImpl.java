package nl.besselking.service.login.impl;

import nl.besselking.dao.UserDAO;
import nl.besselking.domain.User;
import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.service.login.LoginService;
import nl.besselking.service.login.PasswordHashService;

import javax.inject.Inject;

public class LoginServiceImpl implements LoginService {

    public LoginServiceImpl() {
    }

    @Inject
    private UserDAO userDAO;

    @Override
    public User authenticate(String username, String password) throws UnauthorizedUserException {
        User storedUser;
        storedUser = userDAO.findByUserName(username);
        if(storedUser == null)
            throw new UnauthorizedUserException();

        boolean passMatch = PasswordHashService.checkPassword(password, storedUser.getPassword());

        if (!passMatch)
            throw new UnauthorizedUserException();

        storedUser.issueToken();
        userDAO.updateToken(storedUser);

        return storedUser;

    }

    @Override
    public User authToken(String token) throws UnauthorizedUserException {
        User storedUser = userDAO.findByToken(token);
        if (storedUser == null)
            throw new UnauthorizedUserException();
        return storedUser;
    }
}
