package ua.nure.subscriptionservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    @Id @GeneratedValue
    private UUID paymentId;
    private UUID userId;

    private double amount;

    private String stripeCustomerId;
    private String stripeInvoiceId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subscription subscription;

    @Enumerated(EnumType.STRING)
    private String paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;


}
