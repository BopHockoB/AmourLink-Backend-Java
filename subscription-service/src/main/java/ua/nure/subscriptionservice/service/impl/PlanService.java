package ua.nure.subscriptionservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.nure.subscriptionservice.exception.PlanNotFoundException;
import ua.nure.subscriptionservice.model.Plan;
import ua.nure.subscriptionservice.repository.PlanRepository;
import ua.nure.subscriptionservice.service.IPlanService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanService implements IPlanService {

    private final PlanRepository planRepository;

    @Override
    public List<Plan> findAllPlans() {
        return planRepository.findAll();
    }

    @Override
    public Plan findPlanById(UUID planId) {
        return planRepository.findById(planId).orElseThrow(() -> {
            log.warn("Plan {} not found", planId);
            return new PlanNotFoundException("Plan " + planId + " not found");
        });
    }

    @Override
    public Plan findPlanByName(String name) {
        return planRepository.findByPlanName(name).orElseThrow(() -> {
            log.warn("Plan {} not found", name);
            return new PlanNotFoundException("Plan " + name + " not found");
        });
    }

    @Override
    public Plan createPlan(Plan plan) {
        return planRepository.save(plan);
    }

    @Transactional
    @Override
    public Plan updatePlan(Plan updatedPlan) {
            return planRepository.findById(updatedPlan.getPlanId())
                .map(plan -> {
                    plan.setPlanName(updatedPlan.getPlanName());
                    plan.setPlanDescription(updatedPlan.getPlanDescription());
                    plan.setPlanPrice(updatedPlan.getPlanPrice());
                    return planRepository.save(plan);
                }).orElseThrow(  () -> {
                    log.warn("Plan {} not found", updatedPlan.getPlanId());
                    return new PlanNotFoundException("Plan " + updatedPlan.getPlanId() + " not found");
                });
    }

    @Transactional
    @Override
    public void deletePlan(UUID planId){
        planRepository.deleteById(planId);
    }

}
