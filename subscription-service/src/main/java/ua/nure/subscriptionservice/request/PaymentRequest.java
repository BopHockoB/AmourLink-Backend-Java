package ua.nure.subscriptionservice.request;


import com.stripe.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    private String email;
    private Address address;
    private String token;
    private String plan;
}
