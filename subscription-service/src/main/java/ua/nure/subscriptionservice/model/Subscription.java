package ua.nure.subscriptionservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Subscription {
    @Id
    @GeneratedValue
    private UUID subscriptionId;
    private UUID userId;
    private String stripeCustomerId;
    private String stripeSubscriptionId;

    @OneToMany(mappedBy = "subscription")
    private List<Payment> payments;

    @ManyToOne
    private Plan plan;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPaymentDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date nextPaymentDate;


}
