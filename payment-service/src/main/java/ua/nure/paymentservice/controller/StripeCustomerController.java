package ua.nure.paymentservice.controller;

import com.stripe.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.nure.paymentservice.request.CustomerCreateRequest;
import ua.nure.paymentservice.service.StripeCustomerService;

@RestController
@RequestMapping("/api/payment-service/customers")
@RequiredArgsConstructor
public class StripeCustomerController {

    private final StripeCustomerService stripeCustomerService;

    @PostMapping("/create-stripe-customer")
    public Customer createCustomer(@RequestBody CustomerCreateRequest customerCreateRequest) {
        return stripeCustomerService.createCustomer(customerCreateRequest);
    }

    @GetMapping("/get-by-id")
    public Customer retrieveCustomer(@RequestBody String customerId) {
        return stripeCustomerService.retrieveCustomer(customerId);
    }

    @GetMapping("/get-by-email")
    public Customer retrieveCustomerByEmail(@RequestParam String email) {
        return stripeCustomerService.retrieveCustomerByEmail(email);
    }

}
