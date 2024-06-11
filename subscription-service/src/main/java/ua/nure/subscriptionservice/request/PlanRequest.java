package ua.nure.subscriptionservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanRequest {

    private String name;
    private String interval;
    private Double amount;
}
