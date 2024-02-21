package practice.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto  {
    private String message;
    private Integer userId;
    private String userName;
}
