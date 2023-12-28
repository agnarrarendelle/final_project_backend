package practice.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import practice.exception.ProductUnavailableException;
import practice.exception.StripeCheckoutException;
import practice.exception.UsernameDuplicateException;
import practice.result.Result;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameDuplicateException.class)
    public ResponseEntity<?> exception(UsernameDuplicateException exception) {
        return Result.error(exception.getMessage(), UsernameDuplicateException.httpStatus);
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

    @ExceptionHandler(ProductUnavailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> exception(ProductUnavailableException exception) {
        return Result.error(exception.getMessage(), ProductUnavailableException.httpStatus);
    }

    @ExceptionHandler(StripeCheckoutException.class)
    public ResponseEntity<?> exception(StripeCheckoutException exception) {
        return Result.error(exception.getMessage(), StripeCheckoutException.httpStatus);
    }
}
