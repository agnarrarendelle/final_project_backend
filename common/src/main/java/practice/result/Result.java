package practice.result;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class Result<T> {
    private static int SUCCESS_CODE = 10000;
    private static int FAIL_CODE = 10001;

    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = SUCCESS_CODE;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = success();
        result.data = object;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.msg = msg;
        result.code = FAIL_CODE;
        return result;
    }
    public static ResponseEntity<?> error(String msg, HttpStatus statusCode) {
        Result<?> result = error(msg);
        return new ResponseEntity<>(result, statusCode);
    }
}
