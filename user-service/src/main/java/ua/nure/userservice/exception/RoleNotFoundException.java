package ua.nure.userservice.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String s) {
        super(s);
    }
}

