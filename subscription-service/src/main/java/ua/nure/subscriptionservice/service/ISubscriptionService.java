package ua.nure.subscriptionservice.service;

import ua.nure.subscriptionservice.exception.SubscriptionNotFoundException;
import ua.nure.subscriptionservice.model.Subscription;

import java.util.List;
import java.util.UUID;

public interface ISubscriptionService {
    List<Subscription> findAllSubscriptions();

    Subscription findSubscriptionById(UUID subscriptionId) ;

    Subscription createSubscription(Subscription subscription);

    void deleteSubscription(UUID subscriptionId);

    Subscription updateSubscription(Subscription updatedSubscription);

    Subscription findSubscriptionByUserId(UUID userId);
}
