package ua.nure.userservice.exception;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(String s) {
        super(s);
    }
}
