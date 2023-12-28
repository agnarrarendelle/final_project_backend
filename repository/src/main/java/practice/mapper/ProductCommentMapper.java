package practice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import practice.entity.ProductComment;
import practice.vo.productcomment.ProductCommentVo;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductCommentMapper {
    ProductCommentMapper INSTANCE = Mappers.getMapper(ProductCommentMapper.class);

    @Mapping(target = "userImg", source = "entity.user.userImg")
    @Mapping(target = "username", source = "entity.user.username")
    @Mapping(target = "nickname", source = "entity.user.nickname")
    ProductCommentVo toProductCommentVo(ProductComment entity);

    List<ProductCommentVo> toProductCommentVo(List<ProductComment> entities);

}
