package practice.exception;

import org.springframework.http.HttpStatus;

public class TaskStatusNotModifiableException extends RuntimeException {
    public static HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public TaskStatusNotModifiableException(int taskId) {
        super(String.format("Cannot modify task %s. Cannot modify tasks that are expired or finished tasks whose dates are due", taskId));
    }
}
