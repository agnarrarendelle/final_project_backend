package practice.vo.order.orderitem;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemVo {
    String productName;
    int quantity;
    BigDecimal price;
}
