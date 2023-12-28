package practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import practice.dto.shoppingcart.AddShoppingCartDto;
import practice.result.Result;
import practice.service.ShoppingCartService;
import practice.user.CustomUserDetails;
import practice.vo.shoppingcart.ShoppingCartItemVo;

import java.util.List;

@RestController
@RequestMapping("/shop_cart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public Result<Void> addShoppingCart(@RequestBody AddShoppingCartDto dto, @AuthenticationPrincipal CustomUserDetails user){
        dto.setUserId(user.getUserId());
        shoppingCartService.addShoppingCart(dto);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<ShoppingCartItemVo>> listShoppingCart(@AuthenticationPrincipal CustomUserDetails user){
        List<ShoppingCartItemVo> vos = shoppingCartService.listShoppingCartByUserId(user.getUserId());
        return Result.success(vos);
    }
    @GetMapping("/listByIds")
    public Result<List<ShoppingCartItemVo>> listShoppingCart(@AuthenticationPrincipal CustomUserDetails user,@RequestParam List<Integer> cartIds){
        List<ShoppingCartItemVo> vos = shoppingCartService.listShoppingCartByCartIds(user.getUserId(), cartIds);
        return Result.success(vos);
    }


    @PutMapping("/update/{cartId}")
    public Result<Void> updateCartNum(@AuthenticationPrincipal CustomUserDetails user, @PathVariable int cartId, int cartNum){
        shoppingCartService.updateCartNumById(user.getUserId(), cartId, cartNum);
        return Result.success();
    }

}
