package ua.nure.paymentservice.controller;


import com.stripe.model.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.paymentservice.request.ChargeRequest;
import ua.nure.paymentservice.service.StripeSubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/api/payment-service/subscriptions")
@RequiredArgsConstructor
public class StripeSubscriptionController {

    private final StripeSubscriptionService stripeSubscriptionService;

    @PostMapping("/create-subscription")
    public Subscription createSubscription(ChargeRequest chargeRequest) {
        return stripeSubscriptionService.createSubscription(chargeRequest);
    }

    @GetMapping
    public List<Subscription> getCustomerSubscriptions(String customerId){
        return stripeSubscriptionService.retrieveSubscriptions(customerId);
    }

}
