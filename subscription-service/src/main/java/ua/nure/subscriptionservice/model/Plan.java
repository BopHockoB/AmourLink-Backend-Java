package ua.nure.subscriptionservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Plan {

    @Id @GeneratedValue
    private UUID planId;

    private String planName;
    private String planDescription;
    private double planPrice;

    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY)
    private List<Subscription> subscriptions;
}
