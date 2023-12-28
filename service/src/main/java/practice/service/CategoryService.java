package practice.service;

import practice.vo.category.CategoryVo;

import java.util.List;

public interface CategoryService {
    List<CategoryVo> listAllCategories();
    List<CategoryVo> listAllCategories(int categoryLevel);
    List<CategoryVo> listAllCategoriesWithProducts(int categoryLevel);
}
