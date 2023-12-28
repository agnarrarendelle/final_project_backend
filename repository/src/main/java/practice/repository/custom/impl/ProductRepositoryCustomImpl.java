package practice.repository.custom.impl;

import practice.repository.custom.ProductRepositoryCustom;
import practice.utils.PageHelper;
import practice.vo.product.ProductVo;
import practice.vo.productsku.ProductSkuVo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageHelper<ProductVo> findProductsWithCheapestProductSku(int categoryId, int pageNum, int limit) {
        String mainQueryBody =
                "FROM product p " +
                "JOIN product_sku ps ON p.product_id = ps.product_id " +
                "WHERE p.category_id = :categoryId  " +
                "AND (p.product_id, ps.sell_price) IN ( " +
                "    SELECT sub.product_id, MIN(sub.sell_price) " +
                "    FROM product_sku sub " +
                "    WHERE p.product_id = sub.product_id " +
                "    GROUP BY sub.product_id " +
                ") ";
        List<String> selectedColumns = List.of("p.product_id", "p.product_name", "p.sold_num", "ps.sku_name", "ps.sku_img", "ps.sell_price");
        Map<String, String> queryParams = Map.of("categoryId", String.valueOf(categoryId));
        return helper(mainQueryBody, selectedColumns, queryParams, pageNum, limit);
    }

    @Override
    public PageHelper<ProductVo> findProductsByNameContainingWithCheapestProductSku(String keyword, int pageNum, int limit) {
        String mainQueryBody =
                "FROM product p " +
                "JOIN product_sku ps ON p.product_id = ps.product_id " +
                "WHERE p.product_name LIKE BINARY :keyword " +
                "AND (p.product_id, ps.sell_price) IN ( " +
                "    SELECT sub.product_id, MIN(sub.sell_price) " +
                "    FROM product_sku sub " +
                "    WHERE p.product_id = sub.product_id " +
                "    GROUP BY sub.product_id " +
                ") ";
        List<String> selectedColumns = List.of("p.product_id", "p.product_name", "p.sold_num", "ps.sku_name", "ps.sku_img", "ps.sell_price");
        Map<String, String> queryParams = Map.of("keyword", "%" + keyword + "%");
        return helper(mainQueryBody, selectedColumns, queryParams, pageNum, limit);
    }

    private PageHelper<ProductVo> helper(String mainQueryBody, List<String> selectedColumns, Map<String, String> queryParams, int pageNum, int limit){
        String mainQueryString = "SELECT " + String.join(", ", selectedColumns) + " " + mainQueryBody;
        String countQueryString = "SELECT COUNT(*) " + mainQueryBody;

        Query countQuery = entityManager.createNativeQuery(countQueryString);
        queryParams.forEach(countQuery::setParameter);
        int rowCount = getQueryCount(countQuery);
        int totalPageNum = (int) Math.ceil((double) rowCount / limit);

        Query mainQuery = entityManager.createNativeQuery(mainQueryString);
        queryParams.forEach(mainQuery::setParameter);
        mainQuery.setFirstResult((pageNum - 1) * limit).setMaxResults(limit);

        List<Object[]> res = mainQuery.getResultList();

        List<ProductVo> vos = res.stream().map(row -> {
            ProductVo pVo = new ProductVo();
            pVo.setProductId((Integer) row[0]);
            pVo.setProductName((String) row[1]);
            pVo.setSoldNum((Integer) row[2]);

            ProductSkuVo psVo = new ProductSkuVo();
            psVo.setSkuName((String) row[3]);
            psVo.setSkuImg((String) row[4]);
            psVo.setSellPrice((Integer) row[5]);

            pVo.setProductSkus(List.of(psVo));

            return pVo;
        }).toList();
        return new PageHelper<>(vos.size(), totalPageNum, vos);
    }

    private int getQueryCount(Query query) {
        return ((BigInteger) query.getSingleResult()).intValue();
    }
}