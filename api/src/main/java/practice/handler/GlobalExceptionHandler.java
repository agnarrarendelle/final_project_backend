package practice.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import practice.exception.*;
import practice.result.Result;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> exception(UserAlreadyExistException exception) {
        return Result.error(exception.getMessage(), UserAlreadyExistException.httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> exception(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        String errors = result.getFieldErrors().stream().map(err -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.joining(", "));

        return Result.error(errors);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<Void> exception(AuthenticationException exception) {
        return Result.error("Incorrect username or password");
    }

    @ExceptionHandler(GroupNameTakenException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Result<Void> exception(GroupNameTakenException exception) {
        return Result.error(exception.getMessage());
    }

    @ExceptionHandler(InvalidTaskPriorityLevelException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Result<Void> exception(InvalidTaskPriorityLevelException exception) {
        return Result.error(exception.getMessage());
    }

    @ExceptionHandler(InvalidTaskStatusException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Result<Void> exception(InvalidTaskStatusException exception) {
        return Result.error(exception.getMessage());
    }

    @ExceptionHandler(UserNotBelongingInGroupException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Result<Void> exception(UserNotBelongingInGroupException exception) {
        return Result.error(exception.getMessage());
    }

    @ExceptionHandler(TaskNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Result<Void> exception(TaskNotExistException exception) {
        return Result.error(exception.getMessage());
    }
}
