package ua.nure.paymentservice.controller;

import com.stripe.model.Plan;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.paymentservice.request.PlanRequest;
import ua.nure.paymentservice.service.StripePlanService;



@RestController
@RequestMapping("/api/payment-service/plans")
@RequiredArgsConstructor
public class StripePlanController {

    private final StripePlanService stripePlanService;

    @PostMapping("/create-plan")
    public String createPlan(@RequestBody PlanRequest planRequest)  {
       return stripePlanService.createStripePlan(planRequest);
    }

    // Retrieve a plan
    @GetMapping("/{planId}")
    public Plan retrievePlan(@PathVariable String planId) {
        return stripePlanService.getStripePlan(planId);
    }

    // Delete a plan
    @DeleteMapping("/{planId}")
    public void deletePlan(@PathVariable String planId) {
       stripePlanService.deletePlan(planId);
    }
}

