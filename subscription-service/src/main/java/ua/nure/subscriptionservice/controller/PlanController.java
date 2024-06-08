package ua.nure.subscriptionservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.subscriptionservice.response.ResponseBody;
import ua.nure.subscriptionservice.service.impl.PlanService;

@RestController
@RequestMapping("/api/subscription-service/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @GetMapping("/get-all")
    public ResponseEntity<ResponseBody> getAllPlans() {
        return ResponseEntity.ok(
                new ResponseBody(planService.findAllPlans())
        );
    }


}
