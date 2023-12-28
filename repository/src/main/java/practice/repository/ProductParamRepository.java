package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import practice.entity.ProductParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductParamRepository extends JpaRepository<ProductParam, Integer> {
    Optional<ProductParam> findByProductProductId(int productID);

    @Query(value = "select DISTINCT brand from product_params where product_id in " +
                   "(select product_id from product where category_id=:categoryId)",
           nativeQuery = true
    )
    List<String> findBrandByCategoryId(@Param("categoryId") int categoryId);

    @Query(value = "select DISTINCT brand from product_params where product_id in " +
            "(select product_id from product where product_name LIKE BINARY :keyword)",
            nativeQuery = true
    )
    List<String> findBrandsByKeyword(@Param("keyword") String keyword);
}
