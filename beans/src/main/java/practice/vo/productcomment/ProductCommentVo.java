package practice.vo.productcomment;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class ProductCommentVo {
    private String userImg;
    private String username;
    private int isAnonymous;
    private String nickname;
    private Timestamp specName;
    private String commContent;
    private int replyStatus;
    private Timestamp replyTime;
    private String replyContent;
}
