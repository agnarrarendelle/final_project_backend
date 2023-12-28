package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import practice.entity.ProductSku;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, Integer> {

    @Query("SELECT ps.stock from ProductSku ps " +
            "WHERE ps.skuId IN :skuIds "
    )
    List<Integer> findStockNumbers(@Param("skuIds") List<Integer> skuIds);

    @Query("SELECT ps.skuId, ps.stock FROM ProductSku ps WHERE ps.skuId IN :skuIds")
    List<Object[]> countRemainingStockNumbers(@Param("skuIds") List<Integer> skuIds);

    default Map<Integer, Integer> getRemainingStockNumbers(List<Integer> skuIds){
        List<Object[]> obj = countRemainingStockNumbers(skuIds);
        HashMap<Integer, Integer> res = new HashMap<>();
        for(Object[] o: obj){
            Integer skuId = (Integer) o[0];
            Integer stock = (Integer) o[1];

            res.put(skuId, stock);
        }
        return res;
    }
}
