package practice.vo.productcomment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCommentCounterVo {
    private long total;
    private long goodTotal;
    private long midTotal;
    private long badTotal;
    private String percent;
}
