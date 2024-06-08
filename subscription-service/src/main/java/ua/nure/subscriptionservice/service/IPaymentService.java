package ua.nure.subscriptionservice.service;

import ua.nure.subscriptionservice.model.Payment;

import java.util.List;
import java.util.UUID;

public interface IPaymentService {
    List<Payment> findAllPayments();

    List<Payment> findPaymentsByUserId(UUID userID);

    Payment findPaymentById(UUID PaymentId);

    Payment createPayment(Payment payment);

    Payment updatePayment(Payment updatedPayment);

    void deletePayment(UUID paymentId);
}
