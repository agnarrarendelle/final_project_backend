package practice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import practice.vo.TaskVo;

@Builder
@Setter
@Getter
public class TaskWsNotificationDto {
    public enum Type {
        Modified("Modified"),
        Created("Created");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
    private TaskVo task;
    private String type;
}