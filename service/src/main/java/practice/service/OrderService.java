package practice.service;

import practice.utils.PageHelper;
import practice.vo.order.OrderVo;
import practice.dto.order.OrderDto;
import java.util.List;

public interface OrderService {

    public OrderVo addOrder(OrderDto order, int userId);

    public void deleteExpireOrders(List<String> orderIds);

    PageHelper<OrderVo> listOrdersByUserIdAndStatus(int userId, String status, int pageNum, int limit);
}
