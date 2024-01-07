package practice.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserVo {
    private Integer id;
    private String name;
    private String token;
}
