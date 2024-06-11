package ua.nure.subscriptionservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.subscriptionservice.model.Subscription;
import ua.nure.subscriptionservice.request.PaymentRequest;
import ua.nure.subscriptionservice.resolver.UserId;
import ua.nure.subscriptionservice.response.ResponseBody;
import ua.nure.subscriptionservice.service.ISubscriptionService;

import java.util.UUID;

@RestController
@RequestMapping("/api/subscription-service/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final ISubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<ResponseBody> findAllSubscriptions(){
        return ResponseEntity.ok(new ResponseBody
                (subscriptionService.findAllSubscriptions()
        ));
    }

    @GetMapping("/by-user-id")
    public ResponseEntity<ResponseBody> findSubscriptionsByUserId(@UserId UUID userId) {
        return ResponseEntity.ok(new ResponseBody(subscriptionService.findSubscriptionByUserId(userId)));
    }

    @GetMapping("/{subscriptionId}")
    public ResponseEntity<ResponseBody> findSubscriptionById(@PathVariable UUID subscriptionId) {
        return ResponseEntity.ok(new ResponseBody(
                subscriptionService.findSubscriptionById(subscriptionId)
        ));
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseBody> createSubscription(@RequestBody Subscription subscription) {
        return ResponseEntity.ok(new ResponseBody(
                subscriptionService.createSubscription(subscription
                )));
    }

    @DeleteMapping("/{subscriptionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(@PathVariable UUID subscriptionId) {
        subscriptionService.deleteSubscription(subscriptionId);
    }

    @PutMapping("/{subscriptionId}")
    public ResponseEntity<ResponseBody> updateSubscription(@PathVariable UUID subscriptionId, @RequestBody Subscription updatedSubscription) {
        updatedSubscription.setSubscriptionId(subscriptionId);
        return ResponseEntity.ok(new ResponseBody(
                subscriptionService.updateSubscription( updatedSubscription
                )));
    }

    @PostMapping
    public ResponseEntity<ResponseBody> subscribe(@RequestBody PaymentRequest paymentRequest, @UserId UUID userId) {
        return ResponseEntity.ok(new ResponseBody(
                subscriptionService.subscribe(paymentRequest, userId)
        ));
    }
}