package practice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import practice.entity.Category;
import practice.vo.category.CategoryVo;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ProductMapper.class)
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "childCategories", source = "childCategories")
    List<CategoryVo> toVo(List<Category> entities);

    @Mapping(target = "products", source = "products", qualifiedByName = "toVoWithOutProductSku")
    CategoryVo toVo(Category entity);

}
