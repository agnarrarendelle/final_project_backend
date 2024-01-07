package practice.exception;

import org.springframework.http.HttpStatus;

public class GroupNameTakenException extends RuntimeException{
    public static HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public GroupNameTakenException(String groupName) {
        super(String.format("Group name %s has been taken", groupName));
    }
}
