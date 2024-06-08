package ua.nure.subscriptionservice.exception;

public class PlanNotFoundException extends RuntimeException {
    public PlanNotFoundException(String s) {
        super(s);
    }
}
