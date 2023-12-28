package practice.exception;

import org.springframework.http.HttpStatus;

public class ProductUnavailableException extends RuntimeException{
    private static final String message = " Is currently unavailable";

    public static HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public ProductUnavailableException(String product){
        super(product + message);
    }
}
