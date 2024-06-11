package ua.nure.paymentservice.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Plan;
import com.stripe.model.Product;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.nure.paymentservice.request.PlanRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripePlanService {

    @Value("${stripe.secret-key}")
    private String stripeApiKey;

    @PostConstruct
    private void init() {
        // Initialize Stripe with your API key
        Stripe.apiKey = stripeApiKey;
    }

    public String createStripePlan(PlanRequest planRequest) {
        try {

            Map<String, Object> productParam = new HashMap<>();
            productParam.put("name", planRequest.getName());

            Product product = Product.create(productParam);

            Map<String, Object> planParam = new HashMap<>();
            planParam.put("amount", planRequest.getAmount());
            planParam.put("currency", "UAH");
            planParam.put("interval", planRequest.getInterval());
            planParam.put("product", product.getId());

            Plan plan = Plan.create(planParam);

            return plan.getId();

        } catch (StripeException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Plan getStripePlan(String planId) {
        try {
            return Plan.retrieve(planId);

        } catch (StripeException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    public void deletePlan(String planId) {
        try {
            Plan plan = Plan.retrieve(planId);
            plan.delete();

        } catch (StripeException e) {
           log.warn(e.getMessage());
        }
    }
}
