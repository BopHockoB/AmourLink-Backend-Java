package ua.nure.subscriptionservice.client;

import com.stripe.model.Plan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ua.nure.subscriptionservice.request.PlanRequest;

@FeignClient(
        name = "payment-service-plan",
        url = "${application.config.stripe-plan-url}")
public interface PlanClient {

    @PostMapping("/create-plan")
    String createPlan(@RequestBody PlanRequest planRequest);

    @GetMapping("/{planId}")
    Plan retrievePlan(@PathVariable String planId);

    // Delete a plan
    @DeleteMapping("/{planId}")
    void deletePlan(@PathVariable String planId);
}
