package nl.besselking.service.subscription;

import nl.besselking.dao.SubscriberDAO;
import nl.besselking.dao.UserSubscriptionDAO;
import nl.besselking.domain.Subscriber;
import nl.besselking.exceptions.UnauthorizedUserException;
import nl.besselking.service.login.LoginService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class SubscriberService {

    @Inject
    private SubscriberDAO subscriberDAO;

    @Inject
    UserSubscriptionDAO userSubscriptionDAO;

    @Inject
    LoginService loginService;

    public List<Subscriber> getAllSubscribers(String token) throws UnauthorizedUserException {
        loginService.authToken(token);

        List<Subscriber> subbers =  subscriberDAO.list();
        List<Subscriber> responseList = subbers.stream().map(subscriber -> new Subscriber(
                subscriber.getId(),
                subscriber.getFirstname(),
                subscriber.getLastname(),
                subscriber.getEmail()))
                .collect(Collectors.toList());
        return responseList;

    }

    public void shareSubscription(String token, int id, int subscriptionid) throws UnauthorizedUserException {
        loginService.authToken(token);
        userSubscriptionDAO.addUserSubscription(id, subscriptionid);
    }
}
