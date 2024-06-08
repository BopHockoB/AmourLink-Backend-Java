package ua.nure.subscriptionservice.exception;

public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException(String s) {
        super(s);
    }
}
