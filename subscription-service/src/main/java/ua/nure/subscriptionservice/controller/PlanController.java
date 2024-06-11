package ua.nure.subscriptionservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.subscriptionservice.model.Plan;
import ua.nure.subscriptionservice.response.ResponseBody;
import ua.nure.subscriptionservice.service.IPlanService;

import java.util.UUID;

@RestController
@RequestMapping("/api/subscription-service/plans")
@RequiredArgsConstructor
public class PlanController {

    private final IPlanService planService;

    @GetMapping("/get-all")
    public ResponseEntity<ResponseBody> findAllPlans(){
        return ResponseEntity.ok(new ResponseBody
                (planService.findAllPlans()
                ));
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<ResponseBody> findSubscriptionsByUserId(@RequestBody String planName) {
        return ResponseEntity.ok(new ResponseBody(planService.findPlanByName(planName)));
    }

    @GetMapping("/{planId}")
    public ResponseEntity<ResponseBody> findPlanById(@PathVariable UUID planId) {
        return ResponseEntity.ok(new ResponseBody(
                planService.findPlanById(planId)
        ));
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseBody> createPlan(@RequestBody Plan plan) {
        return ResponseEntity.ok(new ResponseBody(
                planService.createPlan(plan)));
    }

    @DeleteMapping("/{planId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlan(@PathVariable UUID planId) {
        planService.deletePlan(planId);
    }

    @PutMapping("/{planId}")
    public ResponseEntity<ResponseBody> updatePlan(@PathVariable UUID planId, @RequestBody Plan updatedPlan) {
        updatedPlan.setPlanId(planId);
        return ResponseEntity.ok(new ResponseBody(
                planService.updatePlan( updatedPlan
                )));
    }

}
