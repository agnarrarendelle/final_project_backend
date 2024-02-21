package practice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Builder
@Setter
@Getter
public class TaskVo {
    private Integer id;
    private String name;
    private String status;
    private String priorityLevel;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp expiredAt;
    private String categoryName;
}
