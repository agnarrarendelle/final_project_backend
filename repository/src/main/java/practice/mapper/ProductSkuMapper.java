package practice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import practice.entity.ProductSku;
import practice.vo.productsku.ProductSkuVo;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductSkuMapper {
    ProductSkuMapper INSTANCE = Mappers.getMapper(ProductSkuMapper.class);

    ProductSkuVo toVo(ProductSku entity);

}
