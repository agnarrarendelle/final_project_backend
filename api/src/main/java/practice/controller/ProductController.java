package practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.result.Result;
import practice.service.ProductService;
import practice.utils.PageHelper;
import practice.vo.productcomment.ProductCommentCounterVo;
import practice.vo.productcomment.ProductCommentVo;
import practice.vo.productparam.ProductParamVo;
import practice.vo.product.ProductVo;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/detail_info/{product_id}")
    public Result<ProductVo> getProductBasicInfo(@PathVariable("product_id") int productId){
        ProductVo vo = productService.getProductBasicInfo(productId);
        return Result.success(vo);
    }

    @GetMapping("/detail_params/{product_id}")
    public Result<ProductParamVo> getProductParams(@PathVariable("product_id") int productId){
        ProductParamVo vo = productService.getProductParamsByProductId(productId);
        return Result.success(vo);
    }

    @GetMapping("/detail_comments/{product_id}")
    public Result<PageHelper<ProductCommentVo>> getProductComments(@PathVariable("product_id") int productId, int pageNum, int limit){
        PageHelper<ProductCommentVo> vo = productService.getProductCommentsByProductId(productId, pageNum, limit);
        return Result.success(vo);
    }

    @GetMapping("/detail_commentscount/{product_id}")
    public Result<ProductCommentCounterVo> getProductCommentCounter(@PathVariable("product_id") int productId){
        ProductCommentCounterVo vo = productService.getProductCommentsCounterByProductId(productId);
        return Result.success(vo);
    }

    @GetMapping("/listbycid/{category_id}")
    public Result<PageHelper<ProductVo>> getProductsByCategoryId(@PathVariable("category_id") int categoryId, int pageNum, int limit){
        PageHelper<ProductVo> vo = productService.getProductsByCategoryId(categoryId, pageNum, limit);
        return Result.success(vo);
    }
    @GetMapping("/listbrands/{category_id}")
    public Result<List<String>> getBrandsByCategoryId(@PathVariable("category_id") int categoryId){
        List<String> vo = productService.listBrands(categoryId);
        return Result.success(vo);
    }

    @GetMapping("/listbrands-keyword")
    public Result<List<String>> getBrandsByKeyword(String keyword){
        List<String> vo = productService.listBrandsByKeyword(keyword);
        return Result.success(vo);
    }

    @GetMapping("/listbykeyword")
    public Result<PageHelper<ProductVo>> searchProducts(String keyword, int pageNum, int limit){
        PageHelper<ProductVo> vo = productService.searchProducts(keyword, pageNum, limit);
        return Result.success(vo);
    }

}
