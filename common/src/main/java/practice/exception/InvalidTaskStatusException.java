package practice.exception;

import org.springframework.http.HttpStatus;

public class InvalidTaskStatusException extends RuntimeException{
    public static HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public InvalidTaskStatusException(String status) {
        super(String.format("%s is not a valid status", status));
    }
}
