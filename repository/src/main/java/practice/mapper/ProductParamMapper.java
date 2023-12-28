package practice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import practice.entity.ProductParam;
import practice.vo.productparam.ProductParamVo;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductParamMapper {
    ProductParamMapper INSTANCE = Mappers.getMapper(ProductParamMapper.class);

    ProductParamVo toProductParamVo(ProductParam entity);

}
