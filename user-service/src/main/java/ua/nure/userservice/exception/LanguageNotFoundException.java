package ua.nure.userservice.exception;

public class LanguageNotFoundException extends RuntimeException {
    public LanguageNotFoundException(String message) {
        super(message);
    }
}
