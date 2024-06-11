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
public class CustomerCreateRequest {

    private String email;
    private String token;
    private Address address;

}
