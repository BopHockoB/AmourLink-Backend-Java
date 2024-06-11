package ua.nure.subscriptionservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String stripeSubscriptionId;

    private SubscriptionStatus status;

    @OneToMany(mappedBy = "subscription",
    cascade = CascadeType.ALL)

    private List<Payment> payments;

    @ManyToOne
    private Plan plan;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPaymentDate;

    private Date canceledAt;

    public enum SubscriptionStatus{
        ACTIVE,
        DISABLE,
        PENDING,
        CANCELLED,
        EXPIRED

    }

}
