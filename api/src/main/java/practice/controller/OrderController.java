package practice.controller;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import practice.dto.order.OrderDto;
import practice.payment.service.StripeService;
import practice.result.Result;
import practice.service.OrderService;
import practice.user.CustomUserDetails;
import practice.utils.PageHelper;
import practice.vo.order.OrderVo;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    StripeService stripeService;

    @PostMapping("/add")
    public Result<OrderVo> addOrder(@AuthenticationPrincipal CustomUserDetails user, @RequestBody OrderDto orderDto) throws StripeException {
        OrderVo orderVo = orderService.addOrder(orderDto, user.getUserId());
        String paymentUrl = stripeService.createCheckoutSession(user.getEmail(), user.getUsername(), orderVo);
        orderVo.setPaymentUrl(paymentUrl);
        return Result.success(orderVo);
    }

    @GetMapping("/list")
    public Result<PageHelper<OrderVo>> listOrders(@AuthenticationPrincipal CustomUserDetails user, @RequestParam(required = false) String status, int pageNum, int limit){
        PageHelper<OrderVo> vos = orderService.listOrdersByUserIdAndStatus(user.getUserId(), status, pageNum, limit);
        return Result.success(vos);
    }
}
