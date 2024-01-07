package practice.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends RuntimeException{
    public static HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public UserAlreadyExistException() {
        super("Account has been registered");
    }
}
