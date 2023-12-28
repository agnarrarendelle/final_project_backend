package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice.entity.ShoppingCart;
import practice.vo.shoppingcart.ShoppingCartItemVo;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    @Query(nativeQuery=true)
    List<ShoppingCartItemVo> findAllByUserIdWithProductSkuAndProduct(@Param("userId") int userId);

    Optional<ShoppingCart> findByUserUserIdAndCartId(int userId, int cartId);

    @Query(nativeQuery=true)
    List<ShoppingCartItemVo> findAllByUserIdAndCartIdsWithProductSkuAndProduct(@Param("cartIds") List<Integer> cartIds, @Param("userId") int userID);

    void deleteByCartIdIn(List<Integer> cartIds);
}
