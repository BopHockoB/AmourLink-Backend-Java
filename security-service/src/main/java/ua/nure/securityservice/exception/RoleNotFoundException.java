package ua.nure.securityservice.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String s) {
        super(s);
    }
}

