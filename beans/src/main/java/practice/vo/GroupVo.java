package practice.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class GroupVo {
    private Integer id;
    private String name;
}
