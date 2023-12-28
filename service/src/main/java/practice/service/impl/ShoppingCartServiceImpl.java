package practice.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.dto.shoppingcart.AddShoppingCartDto;
import practice.entity.Product;
import practice.entity.ProductSku;
import practice.entity.ShoppingCart;
import practice.entity.User;
import practice.mapper.ShoppingCartMapper;
import practice.repository.ShoppingCartRepository;
import practice.service.ShoppingCartService;
import practice.vo.shoppingcart.ShoppingCartItemVo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addShoppingCart(AddShoppingCartDto dto) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(dto, shoppingCart);
        User user = entityManager.getReference(User.class, dto.getUserId());
        Product product = entityManager.getReference(Product.class, dto.getProductId());
        ProductSku productSku = entityManager.getReference(ProductSku.class, dto.getSkuId());

        shoppingCart.setUser(user);
        shoppingCart.setProduct(product);
        shoppingCart.setSku(productSku);

        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<ShoppingCartItemVo> listShoppingCartByUserId(int userId) {
        return shoppingCartRepository.findAllByUserIdWithProductSkuAndProduct(userId);
    }

    @Override
    @Transactional
    public void updateCartNumById(int userId, int cartId, int cartNum) {
        Optional<ShoppingCart> res = shoppingCartRepository.findByUserUserIdAndCartId(userId, cartId);
        if(res.isEmpty()) return;

        ShoppingCart shoppingCart = res.get();
        shoppingCart.setCartNum(cartNum);

        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<ShoppingCartItemVo> listShoppingCartByCartIds(int userId, List<Integer> cartIds) {
        List<ShoppingCartItemVo> vos = shoppingCartRepository.findAllByUserIdAndCartIdsWithProductSkuAndProduct(cartIds, userId);
        return vos;
    }
}
