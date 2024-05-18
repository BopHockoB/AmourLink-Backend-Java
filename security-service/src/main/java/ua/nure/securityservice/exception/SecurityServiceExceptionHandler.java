package ua.nure.securityservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.nure.securityservice.responce.ResponseBody;
import ua.nure.securityservice.responce.ResponseType;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class SecurityServiceExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseBody userNotFound(UserNotFoundException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return new ResponseBody(ResponseType. HTTP_ERROR, null, errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseBody userAlreadyExists(UserAlreadyExistsException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return new ResponseBody(ResponseType.HTTP_ERROR, null, errors);
    }
}