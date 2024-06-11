package ua.nure.subscriptionservice.client;

import com.stripe.model.Subscription;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.nure.subscriptionservice.request.ChargeRequest;

import java.util.List;

@FeignClient(
        name = "payment-service-subscription",
        url = "${application.config.stripe-subscription-url}")
public interface SubscriptionClient {

    @PostMapping("/create-subscription")
    Subscription createSubscription(ChargeRequest chargeRequest);

    @GetMapping
    List<Subscription> getCustomerSubscriptions(String customerId);
}
