package practice.exception;

import org.springframework.http.HttpStatus;

public class CategoryAlreadyExistException extends RuntimeException{
    public static HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public CategoryAlreadyExistException(String categoryName) {
        super(String.format("Category %s already exists", categoryName));
    }
}
