package practice.vo.category;

import lombok.Data;
import practice.vo.product.ProductVo;

import java.util.List;

@Data
public class CategoryVo {
    private Integer categoryId;
    private String categoryName;
    private String categoryIcon;
    private String categorySlogan;
    private String categoryPic;
    private List<CategoryVo> childCategories;
    private List<ProductVo> products;

}
