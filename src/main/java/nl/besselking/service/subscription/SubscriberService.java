package nl.besselking.service.subscription;

import nl.besselking.dao.SubscriberDAO;
import nl.besselking.domain.Subscriber;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

public class SubscriberService {

    @Inject
    private SubscriberDAO subscriberDAO;
    public Response getAllSubscribers(String token){
        List<Subscriber> subbers =  subscriberDAO.list();
        List<SubscriberResponse> responseList = subbers.stream().map(subscriber -> {
            return new SubscriberResponse(
                    subscriber.getId(),
                    subscriber.getFirstname(),
                    subscriber.getEmail());
        }).collect(Collectors.toList());
        return Response.ok(responseList).build();

    }

    public void shareSubscription(String token, int id, int subscriptionid) {
        subscriberDAO.shareSubscription(id, subscriptionid);
    }
}
