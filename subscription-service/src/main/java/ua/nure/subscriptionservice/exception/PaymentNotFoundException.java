package ua.nure.subscriptionservice.exception;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String s) {
        super(s);
    }
}
