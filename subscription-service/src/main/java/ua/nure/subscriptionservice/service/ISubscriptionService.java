package ua.nure.subscriptionservice.service;

import ua.nure.subscriptionservice.model.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ISubscriptionService {
    List<Subscription> findAllSubscriptions();

    Optional<Subscription> findSubscriptionById(UUID subscriptionId);

    Subscription createSubscription(Subscription subscription);

    void deleteSubscription(UUID subscriptionId);

    Subscription updateSubscription(Subscription updatedSubscription);
}
