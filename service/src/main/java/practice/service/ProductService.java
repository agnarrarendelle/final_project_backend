package practice.service;

import practice.utils.PageHelper;
import practice.vo.product.ProductVo;
import practice.vo.productcomment.ProductCommentCounterVo;
import practice.vo.productcomment.ProductCommentVo;
import practice.vo.productparam.ProductParamVo;

import java.util.List;

public interface ProductService {
    public List<ProductVo> listRecommendedProduct();

    ProductVo getProductBasicInfo(int productId);

    ProductParamVo getProductParamsByProductId(int productId);

    PageHelper<ProductCommentVo> getProductCommentsByProductId(int productId, int pageNum, int limit);

    ProductCommentCounterVo getProductCommentsCounterByProductId(int productId);

    PageHelper<ProductVo> getProductsByCategoryId(int categoryId, int pageNum, int limit);

    List<String> listBrands(int categoryId);

    PageHelper<ProductVo> searchProducts(String keyword, int pageNum, int limit);

    List<String> listBrandsByKeyword(String keyword);
}
