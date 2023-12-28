package practice.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import practice.entity.Product;
import practice.repository.custom.ProductRepositoryCustom;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoryCustom {

    @Query("SELECT p FROM Product p JOIN FETCH p.productImages ORDER BY p.createTime DESC")
    List<Product> findByOrderByCreateTimeDesc(Pageable pageable);

    default List<Product> findTopNByOrderByCreateTimeDesc(int limit){
        return findByOrderByCreateTimeDesc(PageRequest.of(0, limit));
    }
}
