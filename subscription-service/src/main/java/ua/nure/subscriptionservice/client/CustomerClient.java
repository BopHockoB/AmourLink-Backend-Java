package ua.nure.subscriptionservice.client;

import com.stripe.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ua.nure.subscriptionservice.request.CustomerCreateRequest;

@FeignClient(
        name = "payment-service-customer",
        url = "${application.config.stripe-customer-url}")
public interface CustomerClient {
    @PostMapping("/create-stripe-customer")
    Customer createCustomer(@RequestBody CustomerCreateRequest customerCreateRequest);

    @GetMapping("/get-by-id")
    Customer retrieveCustomer(@RequestBody String customerId);

    @GetMapping("/get-by-email")
    Customer retrieveCustomerByEmail(@RequestParam String email);

}
