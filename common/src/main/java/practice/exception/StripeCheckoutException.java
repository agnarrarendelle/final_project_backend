package practice.exception;

import org.springframework.http.HttpStatus;

public class StripeCheckoutException extends RuntimeException{
    private static final String message = "Failed to checkout. Please try again later";

    public static HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public StripeCheckoutException(){
        super(message);
    }
}
