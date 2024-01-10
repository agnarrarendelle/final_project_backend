package practice.exception;

import org.springframework.http.HttpStatus;

public class TaskNotExistException extends RuntimeException{
    public static HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public TaskNotExistException(Integer taskId){
        super(String.format("Task %s does not exist", taskId));
    }
}
