package practice.payment.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.payment.service.StripeService;
import practice.payment.utils.CustomerUtil;
import practice.vo.order.OrderVo;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class StripeServiceImpl implements StripeService {
    @Value("${client_urls.root_url}")
    private String clientUrl;

    @Value("${client_urls.success_url}")
    private String successUrl;

    @Value("${client_urls.failed_url}")
    private String failedUrl;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public StripeServiceImpl(@Value("${stripe.api_key}") String stripeKey){
        Stripe.apiKey = stripeKey;
    }

    @Transactional
    public String createCheckoutSession(String email, String name, OrderVo orderVo) throws StripeException {

        Customer customer = CustomerUtil.findOrCreateCustomer(email, name);

        Map<String, String> metadata = Map.of("orderId", orderVo.getOrderId(), "username", name);

        SessionCreateParams.Builder paramsBuilder =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setCustomer(customer.getId())
                        .putAllMetadata(metadata)
                        .setSuccessUrl(successUrl)
                        .setCancelUrl(failedUrl);

        orderVo.getOrderItems().forEach(orderItem->{
            paramsBuilder.addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity((long) orderItem.getQuantity())
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("USD")
                                            .setUnitAmount((orderItem.getPrice().longValue() * 100))
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName(orderItem.getProductName())
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            );
        });

        Session session = Session.create(paramsBuilder.build());

        stringRedisTemplate.opsForValue().set(orderVo.getOrderId(), session.getId(), 2, TimeUnit.HOURS);

        return session.getUrl();

    }
}
