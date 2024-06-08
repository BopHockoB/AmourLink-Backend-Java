package ua.nure.subscriptionservice.model;

public enum PaymentStatus {
    SUCCEEDED("succeeded"),
    INCOMPLETE("incomplete"),
    FAILED("failed"),
    UNCAPTURED("uncaptured"),
    CANCELED("canceled"),
    REFUNDED("refunded");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
