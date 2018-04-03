package nl.besselking.service.subscription;

import nl.besselking.dao.SubscriptionDAO;
import nl.besselking.dao.UserSubscriptionDAO;
import nl.besselking.domain.Subscription;
import nl.besselking.domain.User;
import nl.besselking.domain.UserSubscription;
import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.rest.dto.DetailedSubscriptionResponse;
import nl.besselking.rest.dto.SubscriptionListResponse;
import nl.besselking.service.login.LoginService;

import javax.inject.Inject;
import java.util.List;

public class SubscriptionService {

    @Inject
    LoginService loginService;
    @Inject
    UserSubscriptionDAO userSubscriptionDAO;
    @Inject
    SubscriptionDAO subscriptionDAO;

    public SubscriptionListResponse getAllUserSubscriptions(String token) throws UnauthorizedUserException {
        User storedUser = loginService.authToken(token);
        List<Subscription> subList = userSubscriptionDAO.allUserSubscriptions(storedUser.getId());
        double totalPrice = userSubscriptionDAO.getTotalPrice(storedUser.getId());
        return new SubscriptionListResponse(subList, totalPrice);
    }

    public DetailedSubscriptionResponse getSubscription(String token, int id) throws UnauthorizedUserException {
        User storedUser = loginService.authToken(token);
        UserSubscription sub = userSubscriptionDAO.getUserSubscription(storedUser.getId(), id);
        return new DetailedSubscriptionResponse(sub);
    }

    public void terminate(String token, int id) throws UnauthorizedUserException {
        User storedUser = loginService.authToken(token);
        userSubscriptionDAO.terminateSubscription(storedUser.getId(), id);
    }

    public void addNewUserSubscription(String token, int subscriptionId) throws UnauthorizedUserException {
        User storedUser = loginService.authToken(token);
        userSubscriptionDAO.addUserSubscription(storedUser.getId(), subscriptionId);
    }

    public List<Subscription> getAllSubscriptions(String token, String filter) throws UnauthorizedUserException {
        loginService.authToken(token);
        List<Subscription> subs = subscriptionDAO.list(filter);
        return subs;
    }

    public void upgradeSubscription(String token, int id, String verdubbeling) throws UnauthorizedUserException {
        User storedUser = loginService.authToken(token);
        userSubscriptionDAO.upgradeSubscription(id, storedUser.getId(), verdubbeling);
    }
}
