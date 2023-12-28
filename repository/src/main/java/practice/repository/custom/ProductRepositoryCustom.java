package practice.repository.custom;

import practice.utils.PageHelper;
import practice.vo.product.ProductVo;


public interface ProductRepositoryCustom {
    PageHelper<ProductVo> findProductsWithCheapestProductSku(int categoryId, int pageNum, int limit);
    PageHelper<ProductVo> findProductsByNameContainingWithCheapestProductSku(String keyword, int pageNum, int limit);
}
