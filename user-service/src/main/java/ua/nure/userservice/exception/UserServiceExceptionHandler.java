package ua.nure.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.nure.userservice.model.Profile;
import ua.nure.userservice.responce.ResponseBody;
import ua.nure.userservice.responce.ResponseType;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserServiceExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseBody handleException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseBody(ResponseType.VALIDATION_FAILED, null, errors);
    }

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProfileAlreadyExistsException.class)
    public ResponseBody profileAlreadyExists(ProfileAlreadyExistsException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return new ResponseBody(ResponseType.HTTP_ERROR, null, errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseBody profileNotFound(ProfileNotFoundException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return new ResponseBody(ResponseType.HTTP_ERROR, null, errors);
    }
}