package practice.service.job;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.entity.Order;
import practice.repository.OrderRepository;
import practice.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class OrderTimeoutCheckJob {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Scheduled(cron = "0/60 * * * * ?")
    @Transactional
    public void checkAndCloseOrder() {
        List<Order> expiredOrders = orderRepository.findExpiredOrders();
        List<String> expiredOrderIds = new ArrayList<>();
        List<String> finishedOrderIds = new ArrayList<>();
        for (Order order : expiredOrders) {
            String orderId = order.getOrderId();
            //If the order id is no longer in redis, it must have been completed and deleted
            if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(orderId))) {
                finishedOrderIds.add(orderId);
                continue;
            }

            String sessionId = stringRedisTemplate.opsForValue().get(orderId);

            if(Objects.isNull(sessionId))
                continue;

            stringRedisTemplate.delete(orderId);

            try {
                Session session = Session.retrieve(sessionId);
                if (session.getPaymentStatus().equals("paid")) {
                    finishedOrderIds.add(orderId);
                    continue;
                }
                session.expire();
                expiredOrderIds.add(orderId);
            } catch (StripeException e) {
                System.out.println(e.getMessage());
            }

        }

        if(!finishedOrderIds.isEmpty())
            orderRepository.updateToNewStatus("2", finishedOrderIds);

        if(!expiredOrders.isEmpty())
            orderService.deleteExpireOrders(expiredOrderIds);
    }
}
