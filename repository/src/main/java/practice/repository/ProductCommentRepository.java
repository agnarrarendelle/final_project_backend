package practice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import practice.entity.ProductComment;

import java.util.HashMap;
import java.util.List;

@Repository
public interface ProductCommentRepository extends JpaRepository<ProductComment, Integer> {
    @Query(value = "SELECT pc FROM ProductComment pc " +
            "JOIN FETCH pc.product p " +
            "JOIN FETCH pc.user u " +
            "WHERE pc.product.productId = :productId ",
            countQuery = "SELECT COUNT(pc) FROM ProductComment pc WHERE pc.product.productId = :productId"
    )
    Page<ProductComment> findAllByProductProductId(@Param("productId") int productId, Pageable pageable);

    @Query("SELECT pc.commType, COUNT(*) " +
            "FROM ProductComment pc " +
            "WHERE pc.product.productId = :productId " +
            "GROUP BY pc.commType "
    )
    List<Object[]> getCommentTypes(@Param("productId") int productId);

    default HashMap<Integer, Long> countCommentType(int productId) {
        List<Object[]> obj = getCommentTypes(productId);
        HashMap<Integer, Long> res = new HashMap<>(){{
            put(-1,0L);
            put(0,0L);
            put(1,0L);
        }};
        obj.forEach((o) -> {
            int key = (Integer) o[0];
            long val = (Long) o[1];
            res.put(key, val);
        });

        return res;
    }
}
