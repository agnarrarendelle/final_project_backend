package practice.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginVo {
    String token;
    int userId;
    String username;
    String userImg;
}
