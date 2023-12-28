package practice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.entity.Category;
import practice.mapper.CategoryMapper;
import practice.repository.CategoryRepository;
import practice.service.CategoryService;
import practice.vo.category.CategoryVo;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;
    @Override
    @Transactional
    public List<CategoryVo> listAllCategories() {
        List<CategoryVo> vos = listAllCategories(1);
        return listAllCategories(1);
    }

    @Override
    public List<CategoryVo> listAllCategories(int categoryLevel) {
        List<Category> categoriesWithChildCategories = categoryRepository.findAllWithChildCategories(categoryLevel);
        return categoryMapper.toVo(categoriesWithChildCategories);
    }

    @Override
    @Transactional
    public List<CategoryVo> listAllCategoriesWithProducts(int categoryLevel) {
        List<Category> entities = categoryRepository.findAllWithAllChildProducts(categoryLevel);
        return categoryMapper.toVo(entities);
    }
}
