package practice.vo.order;

import lombok.Builder;
import lombok.Data;
import practice.vo.order.orderitem.OrderItemVo;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class OrderVo {
    private String untitled;
    private String orderId;
    private List<OrderItemVo> orderItems;
    private String paymentUrl;
    private String status;
    private Timestamp createTime;
}
