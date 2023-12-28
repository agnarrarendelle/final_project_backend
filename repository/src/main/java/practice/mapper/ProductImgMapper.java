package practice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import practice.entity.ProductImg;
import practice.vo.ImgVo;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductImgMapper {
    ProductImgMapper INSTANCE = Mappers.getMapper(ProductImgMapper.class);

    @Mapping(source = "url", target = "imgUrl")
    ImgVo toVo(ProductImg entity);

    List<ImgVo> toVo(List<ProductImg> entities);
}
