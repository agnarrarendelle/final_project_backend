package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    @Query(
            "SELECT o " +
            "FROM Order o " +
            "JOIN FETCH o.orderItems "+
            "WHERE o.orderId = :orderId"
    )
    Optional<Order> findByIdWithOrderItems(@Param("orderId") String orderId);

    @Query(value =
            "SELECT * " +
            "FROM orders " +
            "WHERE status = '1' AND create_time < NOW() - INTERVAL 30 MINUTE;",
            nativeQuery = true)
    List<Order> findExpiredOrders();

    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.orderId IN :orderIds")
    void updateToNewStatus(@Param("status") String status, @Param("orderIds") List<String> orderIds);

    @Modifying
    @Query("UPDATE Order o SET o.status = '6', closeType = 1 WHERE o.orderId IN :orderIds")
    void expireOrders(@Param("orderIds") List<String> orderIds);
}
