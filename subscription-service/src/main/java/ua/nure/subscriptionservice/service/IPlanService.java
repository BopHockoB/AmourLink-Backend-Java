package ua.nure.subscriptionservice.service;

import jakarta.transaction.Transactional;
import ua.nure.subscriptionservice.model.Plan;

import java.util.List;
import java.util.UUID;

public interface IPlanService {
    List<Plan> findAllPlans();

    Plan findPlanById(UUID planId);

    Plan findPlanByName(String name);

    Plan createPlan(Plan plan);

    Plan updatePlan(Plan updatedPlan);

    @Transactional
    void deletePlan(UUID planId);
}
