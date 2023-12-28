package practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.result.Result;
import practice.service.CategoryService;
import practice.service.IndexImgService;
import practice.service.ProductService;
import practice.vo.*;
import practice.vo.category.CategoryVo;
import practice.vo.product.ProductVo;

import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    IndexImgService indexImgService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @GetMapping("/index_imgs")
    public Result<List<ImgVo>> listIndexImgs(){
        List<ImgVo> indexImgVos = indexImgService.listIndexImgs();
        return Result.success(indexImgVos);
    }

    @GetMapping("/category_list")
    public Result<List<CategoryVo>> listCategories(){
        List<CategoryVo> categoryVos = categoryService.listAllCategories();
        return Result.success(categoryVos);
    }

    @GetMapping("/list_recommends")
    public Result<List<ProductVo>> listRecommendedProducts(){
        List<ProductVo> vos = productService.listRecommendedProduct();
        return Result.success(vos);
    }

    @GetMapping("/category_recommends")
    public Result<List<CategoryVo>> listRecommendedProductsByCategory(){
        List<CategoryVo> productVos = categoryService.listAllCategoriesWithProducts(1);
        return Result.success(productVos);
    }
}
