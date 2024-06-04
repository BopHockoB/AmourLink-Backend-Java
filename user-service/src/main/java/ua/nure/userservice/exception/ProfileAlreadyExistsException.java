package ua.nure.userservice.exception;

public class ProfileAlreadyExistsException extends RuntimeException {
    public ProfileAlreadyExistsException(String s) {
        super(s);
    }
}
