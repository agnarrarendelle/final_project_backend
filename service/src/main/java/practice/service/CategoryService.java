package practice.service;

import practice.dto.CategoryDto;
import practice.vo.CategoryVo;

import java.util.List;

public interface CategoryService {
    CategoryVo addCategory(Integer groupId, String categoryName);

    List<CategoryVo> getCategories(Integer userId, Integer groupId);
}
