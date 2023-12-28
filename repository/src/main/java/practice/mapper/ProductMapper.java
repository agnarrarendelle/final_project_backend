package practice.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import practice.entity.Product;
import practice.entity.ProductImg;
import practice.vo.ImgVo;
import practice.vo.product.ProductVo;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ProductSkuMapper.class, ProductImgMapper.class})
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductVo toVo(Product entity);

    @Mapping(target ="productSkus", ignore = true )
    @Named(value = "toVoWithOutProductSku")
    ProductVo toVoWithOutProductSku(Product entity);
    @Mapping(target ="productSkus", ignore = true )
    @Mapping(target ="productImages", ignore = true )
    @Named(value = "toVoWithOutProductSkuAndProductImages")
    ProductVo toVoWithOutProductSkuAndProductImages(Product entity);

    List<ProductVo> toVos(List<Product> entities);

    @IterableMapping(qualifiedByName="toVoWithOutProductSku")
    List<ProductVo> toVosWithOutProductSku(List<Product> entities);

    @IterableMapping(qualifiedByName="toVoWithOutProductSkuAndProductImages")
    List<ProductVo> toVosWithOutProductSkuAndProductImages(List<Product> entities);

}
