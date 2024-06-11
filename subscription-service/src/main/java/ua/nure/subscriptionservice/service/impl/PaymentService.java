package ua.nure.subscriptionservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.nure.subscriptionservice.exception.PaymentNotFoundException;
import ua.nure.subscriptionservice.model.Payment;
import ua.nure.subscriptionservice.repository.PaymentRepository;
import ua.nure.subscriptionservice.service.IPaymentService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public List<Payment> findAllPayments(){
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> findPaymentsByUserId(UUID userID){
        return paymentRepository.findAllByUserId(userID);
    }

    @Override
    public Payment findPaymentById(UUID paymentId){
        return paymentRepository.findById(paymentId).orElseThrow(
                ()->{
                    log.warn("Payment with id {} not found", paymentId);
                    return new PaymentNotFoundException("Payment with id " + paymentId + " not found");
                }
        );
    }

    @Override
    public Payment createPayment(Payment payment){
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Payment updatedPayment){
        return paymentRepository.findById(updatedPayment.getPaymentId())
                .map(payment -> {
                    payment.setUserId(updatedPayment.getUserId());
                    payment.setStripeCustomerId(updatedPayment.getStripeCustomerId());
                    payment.setAmount(updatedPayment.getAmount());
                    payment.setPaymentDate(updatedPayment.getPaymentDate());
                    payment.setSubscription(updatedPayment.getSubscription());
                    payment.setPaymentStatus(updatedPayment.getPaymentStatus());
                    payment.setPaymentType(updatedPayment.getPaymentType());
                    return paymentRepository.save(payment);
                }).orElseThrow(  () -> {
                    log.warn("Payment with id {} not found", updatedPayment.getPaymentId());
                    return new PaymentNotFoundException("Payment with id " + updatedPayment.getPaymentId() + " not found");
                });
    }

    @Override
    public void deletePayment(UUID paymentId) {
        paymentRepository.deleteById(paymentId);
    }

}
