package ua.nure.subscriptionservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.nure.subscriptionservice.exception.SubscriptionNotFoundException;
import ua.nure.subscriptionservice.model.Subscription;
import ua.nure.subscriptionservice.repository.SubscriptionRepository;
import ua.nure.subscriptionservice.service.ISubscriptionService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> findAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription findSubscriptionById(UUID subscriptionId){
        return subscriptionRepository.findById(subscriptionId).orElseThrow(
                () -> {
                    log.warn("Subscription not found: {}", subscriptionId);
                    return new SubscriptionNotFoundException("Subscription not found: " + subscriptionId );
                });
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
                    subscription.setPayments(updatedSubscription.getPayments());
                    subscription.setPlan(updatedSubscription.getPlan());
                    subscription.setStartDate(updatedSubscription.getStartDate());
                    subscription.setEndDate(updatedSubscription.getEndDate());
                    subscription.setLastPaymentDate(updatedSubscription.getLastPaymentDate());
                    subscription.setNextPaymentDate(updatedSubscription.getNextPaymentDate());
                    return subscriptionRepository.save(subscription);
                }).orElseThrow(  () -> {
                    log.warn("Subscription not found: {}", updatedSubscription.getSubscriptionId());
                    return new SubscriptionNotFoundException("Subscription not found: " + updatedSubscription.getSubscriptionId() );
                });
    }

    @Override
    public Subscription findSubscriptionByUserId(UUID userId) {
        return subscriptionRepository.findByUserId(userId).orElseThrow(
                () -> {
                    log.warn("Subscription for user {} not found", userId);
                    return new SubscriptionNotFoundException("Subscription for user " + userId + " not found");
                });
    }

}