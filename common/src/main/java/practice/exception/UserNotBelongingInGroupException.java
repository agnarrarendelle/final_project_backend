package practice.exception;

import org.springframework.http.HttpStatus;

public class UserNotBelongingInGroupException extends RuntimeException{
    public static HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public UserNotBelongingInGroupException(Integer userID){
        super(String.format("User %s cannot this access this group", userID));
    }
}
