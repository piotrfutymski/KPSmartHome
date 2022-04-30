package kp.config;

import kp.user.service.InvalidOldPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidOldPasswordException.class)
    public String handleInvalidOldPasswordException(InvalidOldPasswordException exception){
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UsernameNotFoundException.class})
    public String handleNotFountException(Exception exception){
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public String handleBadRequestException(Exception exception){
        return exception.getMessage();
    }
}
