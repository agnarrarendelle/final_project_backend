package practice.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import practice.payment.service.impl.StripeServiceImpl;
import practice.repository.OrderRepository;
import practice.vo.order.OrderVo;
import practice.vo.order.orderitem.OrderItemVo;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StripeServiceTests {


    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    StripeServiceImpl stripeService = new StripeServiceImpl(System.getenv("STRIPE_API_KEY"));

    @BeforeAll
    public void beforeAll() {
        String stripeKey = System.getenv("STRIPE_API_KEY");
        String clientUrl = "http://127.0.0.1:5500";
        String redirectedUrl = clientUrl + "/index.html";
        ReflectionTestUtils.setField(stripeService, "clientUrl", clientUrl);
        ReflectionTestUtils.setField(stripeService, "successUrl", redirectedUrl);
        ReflectionTestUtils.setField(stripeService, "failedUrl", redirectedUrl);
        Stripe.apiKey = stripeKey;
    }

    @Test
    public void test() throws StripeException {
        String email = "danny20001102@gmail.com";
        String name = "Matthew Huang";
        OrderVo orderVo = OrderVo.builder()
                .orderId("test id")
                .orderItems(List.of(OrderItemVo.builder()
                        .price(BigDecimal.valueOf(100))
                        .quantity(1)
                        .productName("test product name")
                        .build()))
                .build();
        String successUrl = "https://www.youtube.com/";
        String failedUrl = "https://www.youtube.com/";
        stripeService.createCheckoutSession(email, name, orderVo);
    }
}
