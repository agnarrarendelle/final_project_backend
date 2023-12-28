package practice.service;

import practice.dto.shoppingcart.AddShoppingCartDto;
import practice.vo.shoppingcart.ShoppingCartItemVo;

import java.util.List;

public interface ShoppingCartService {
    void addShoppingCart(AddShoppingCartDto dto);

    List<ShoppingCartItemVo> listShoppingCartByUserId(int userId);

    void updateCartNumById(int userId, int cartId, int cartNum);

    List<ShoppingCartItemVo> listShoppingCartByCartIds(int userId, List<Integer> cartIds);
}
