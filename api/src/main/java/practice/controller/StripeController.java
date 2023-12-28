package practice.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import practice.entity.Order;
import practice.exception.StripeCheckoutException;
import practice.repository.OrderRepository;

import java.util.Optional;

@RestController
@RequestMapping("/stripe")
public class StripeController {
    private final String webHookSecret = "whsec_e41807659951d1491d6fffcc15cdbf49395d3417c1ca9810e6c85ac399deb2c6";

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @PostMapping("/stripe_webhook")
    @Transactional
    public void handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) throws SignatureVerificationException {
        Event event = Webhook.constructEvent(payload, sigHeader, webHookSecret);
        if (event.getType().equals("checkout.session.completed")) {
            if (event.getDataObjectDeserializer().getObject().isEmpty()) {
                throw new StripeCheckoutException();
            }
            Session session = (Session) event.getDataObjectDeserializer().getObject().get();

            String orderId = session.getMetadata().get("orderId");
            String username = session.getMetadata().get("username");

            Optional<Order> res = orderRepository.findById(orderId);
            if (res.isEmpty()) {
                throw new StripeCheckoutException();
            }
            Order order = res.get();

            order.setStatus("2");
            orderRepository.save(order);

            stringRedisTemplate.delete(orderId);
            try {
                String successNotificationMsg = String.format("Your order %s has been successfully created", order.getOrderId());
                simpMessagingTemplate.convertAndSendToUser(username, "/order_status", successNotificationMsg);
            } catch (Exception e) {

            }
        }
    }
}
