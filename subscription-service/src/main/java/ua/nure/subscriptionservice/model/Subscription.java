package ua.nure.subscriptionservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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
    private String planId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPaymentDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date nextPaymentDate;

            @Getter
            @AllArgsConstructor
            public enum Status {
                INCOMPLETE("incomplete"),
                INCOMPLETE_EXPIRED("incomplete_expired"),
                TRIALING("trialing"),
                ACTIVE("active"),
                PAST_DUE("past_due"),
                CANCELED("canceled"),
                UNPAID("unpaid");

                private final String status;
            }
}
