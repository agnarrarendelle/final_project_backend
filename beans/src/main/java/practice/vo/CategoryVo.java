package practice.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CategoryVo {
    private Integer id;
    private String name;
}
