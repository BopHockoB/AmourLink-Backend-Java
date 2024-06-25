package ua.nure.subscriptionservice.service.impl;

import com.stripe.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.nure.subscriptionservice.client.CustomerClient;
import ua.nure.subscriptionservice.client.SubscriptionClient;
import ua.nure.subscriptionservice.exception.SubscriptionCreationException;
import ua.nure.subscriptionservice.exception.SubscriptionNotFoundException;
import ua.nure.subscriptionservice.model.Plan;
import ua.nure.subscriptionservice.model.Subscription;
import ua.nure.subscriptionservice.service.impl.repository.SubscriptionRepository;
import ua.nure.subscriptionservice.request.ChargeRequest;
import ua.nure.subscriptionservice.request.CustomerCreateRequest;
import ua.nure.subscriptionservice.request.PaymentRequest;
import ua.nure.subscriptionservice.service.IPlanService;
import ua.nure.subscriptionservice.service.ISubscriptionService;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    private final IPlanService planService;

    private final SubscriptionClient subscriptionClient;
    private final CustomerClient customerClient;

    @Override
    public List<Subscription> findAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription findSubscriptionById(UUID subscriptionId) {
        return subscriptionRepository.findById(subscriptionId).orElseThrow(
                () -> {
                    log.warn("Subscription not found: {}", subscriptionId);
                    return new SubscriptionNotFoundException("Subscription not found: " + subscriptionId);
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
                    subscription.setStripeSubscriptionId(updatedSubscription.getStripeSubscriptionId());
                    subscription.setPayments(updatedSubscription.getPayments());
                    subscription.setPlan(updatedSubscription.getPlan());
                    subscription.setStartDate(updatedSubscription.getStartDate());
                    subscription.setEndDate(updatedSubscription.getEndDate());
                    subscription.setLastPaymentDate(updatedSubscription.getLastPaymentDate());
                    return subscriptionRepository.save(subscription);
                }).orElseThrow(() -> {
                    log.warn("Subscription not found: {}", updatedSubscription.getSubscriptionId());
                    return new SubscriptionNotFoundException("Subscription not found: " + updatedSubscription.getSubscriptionId());
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

    @Override
    public Subscription subscribe(PaymentRequest paymentRequest, UUID userId) {

        Plan plan = planService.findPlanByName(paymentRequest.getEmail());
        Customer stripeCustomer = customerClient.retrieveCustomerByEmail(paymentRequest.getEmail());

        if (stripeCustomer == null)
            stripeCustomer = customerClient.createCustomer(CustomerCreateRequest.builder()
                    .email(paymentRequest.getEmail())
                    .token(paymentRequest.getToken())
                    .address(paymentRequest.getAddress())
                    .build());


        com.stripe.model.Subscription stripeSubscription = subscriptionClient.createSubscription(ChargeRequest.builder()
                .planId(plan.getStripeId())
                .customerId(stripeCustomer.getId())
                .build());

        if (stripeSubscription == null ||
                stripeSubscription
                        .getLatestInvoiceObject()
                        .getPaymentIntentObject()
                        .getStatus().equals("success"))
            throw new SubscriptionCreationException("An issue occurred while creating subscription for user " + userId);

        Subscription subscription = Subscription.builder()
                .userId(userId)
                .stripeSubscriptionId(stripeSubscription.getId())
                .plan(plan)
                .status(Subscription.SubscriptionStatus.ACTIVE)
                .startDate(new Date(stripeSubscription.getStartDate()))
                .endDate(new Date(stripeSubscription.getEndedAt()))
                .build();

        return subscriptionRepository.save(subscription);
    }


}