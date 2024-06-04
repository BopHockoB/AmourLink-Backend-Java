package ua.nure.subscriptionservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.subscriptionservice.model.Subscription;
import ua.nure.subscriptionservice.repository.SubscriptionRepository;
import ua.nure.subscriptionservice.service.ISubscriptionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> findAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Optional<Subscription> findSubscriptionById(UUID subscriptionId) {
        return subscriptionRepository.findById(subscriptionId);
    }

    @Override
    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public void deleteSubscription(UUID subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }

    @Override
    public Subscription updateSubscription(Subscription updatedSubscription) {
        return subscriptionRepository.findById(updatedSubscription.getSubscriptionId())
                .map(subscription -> {
                    subscription.setUserId(updatedSubscription.getUserId());
                    subscription.setStripeCustomerId(updatedSubscription.getStripeCustomerId());
                    subscription.setStripeSubscriptionId(updatedSubscription.getStripeSubscriptionId());
                    subscription.setPlanId(updatedSubscription.getPlanId());
                    subscription.setStatus(updatedSubscription.getStatus());
                    subscription.setStartDate(updatedSubscription.getStartDate());
                    subscription.setEndDate(updatedSubscription.getEndDate());
                    subscription.setLastPaymentDate(updatedSubscription.getLastPaymentDate());
                    subscription.setNextPaymentDate(updatedSubscription.getNextPaymentDate());
                    return subscriptionRepository.save(subscription);
                }).orElseThrow(() -> new RuntimeException("Subscription Not Found"));
    }

}