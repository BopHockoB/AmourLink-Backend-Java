package ua.nure.paymentservice.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.param.CustomerCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.nure.paymentservice.request.CustomerCreateRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripeCustomerService {

    @Value("${stripe.secret-key}")
    private String stripeApiKey;

    @PostConstruct
    private void init() {
        // Initialize Stripe with your API key
        Stripe.apiKey = stripeApiKey;
    }

    public Customer createCustomer(CustomerCreateRequest customerCreateRequest)  {
        try {
        CustomerCreateParams createParams = CustomerCreateParams.builder()
                .setDescription("Customer for " + customerCreateRequest.getEmail())
                .setEmail(customerCreateRequest.getEmail())
                .setSource(customerCreateRequest.getToken()) // This is the card token/id
                .build();

            return Customer.create(createParams);
        } catch (StripeException e) {
           log.error(e.getMessage());
           return null;
        }

    }

    public Customer retrieveCustomer(String customerId)  {
        try {
            return Customer.retrieve(customerId);
        } catch (StripeException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    public Customer retrieveCustomerByEmail(String email)  {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("email", email);

            CustomerCollection customers = Customer.list(params);
            return customers.getData().getFirst();

        } catch (StripeException e){
            log.warn(e.getMessage());
        }
        return null;
    }


}
