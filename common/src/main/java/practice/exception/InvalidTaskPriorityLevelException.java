package practice.exception;

import org.springframework.http.HttpStatus;

public class InvalidTaskPriorityLevelException extends RuntimeException{
    public static HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public InvalidTaskPriorityLevelException(String priorityLevel) {
        super(String.format("%s is not a valid priority level", priorityLevel));
    }
}
