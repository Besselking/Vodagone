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
import javax.ws.rs.core.Response;
import java.util.List;

public class SubscriptionService {

    @Inject
    LoginService loginService;
    @Inject
    UserSubscriptionDAO userSubscriptionDAO;
    @Inject
    SubscriptionDAO subscriptionDAO;

    public Response getAllUserSubscriptions(String token) {
        User storedUser;
        try {
            storedUser = loginService.authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        SubscriptionListResponse subList = userSubscriptionDAO.allUserSubscriptions(storedUser.getId());


        return Response.ok(subList).build();
    }

    public Response getSubscription(String token, int id){
        User storedUser;
        try {
            storedUser = loginService.authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        UserSubscription sub = userSubscriptionDAO.getUserSubscription(storedUser.getId(), id);


        return Response.ok(new DetailedSubscriptionResponse(sub)).build();
    }

    public Response terminate(String token, int id) {
        User storedUser;
        try {
            storedUser = loginService.authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        userSubscriptionDAO.terminateSubscription(storedUser.getId(), id);
        return getSubscription(token, id);

    }

    public Response addNewUserSubscription(String token, int subscriptionId) {
        User storedUser;
        try {
            storedUser = loginService.authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        userSubscriptionDAO.addUserSubscription(storedUser.getId(), subscriptionId);

        return getAllUserSubscriptions(token);
    }

    public Response getAllSubscriptions(String token, String filter) {
        try {
            loginService.authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }


        List<Subscription> subs = subscriptionDAO.list(filter);

        return Response.ok(subs).build();
    }

    public Response upgradeSubscription(String token, int id) {
        try {
            loginService.authToken(token);
        } catch (UnauthorizedUserException e) {
            return Response.status(403).build();
        }
        return Response.ok().build();
    }
}
