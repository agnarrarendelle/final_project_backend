package practice.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

public class UsernameDuplicateException extends RuntimeException  {
    private static final String message = " Username has been taken";
    public static HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public UsernameDuplicateException(String username){
        super(username + message);
    }
}
