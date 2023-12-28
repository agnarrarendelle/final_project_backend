package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice.entity.OrderItem;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    List<OrderItem> findByOrderOrderIdIn(List<Long> orderIds);

    @Query(value = "SELECT sku_id, SUM(buy_counts) AS totalBuyCount " +
                   "FROM order_item WHERE order_id IN :orderIds " +
                   "GROUP BY sku_id ;", nativeQuery = true)
    List<Object[]> findOrderItemBuyCountsInterface(@Param("orderIds") List<String> orderIds);

    default Map<Integer, Integer> findOrderItemBuyCountsInterfaceImpl(List<String> orderIds){
        List<Object[]> obj = findOrderItemBuyCountsInterface(orderIds);
        HashMap<Integer, Integer> res = new HashMap<>();
        for(Object[] o: obj){
            Integer skuId = (Integer) o[0];
            Integer buyCounts = ((BigDecimal) o[1]).intValue();

            res.put(skuId, buyCounts);
        }
        return res;
    }
}
