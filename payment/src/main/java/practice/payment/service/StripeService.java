package practice.payment.service;

import com.stripe.exception.StripeException;
import practice.vo.order.OrderVo;

import java.util.List;

public interface StripeService {
    public String createCheckoutSession(String email, String name, OrderVo orderVo) throws StripeException;
}
