package practice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import practice.entity.IndexImg;
import practice.vo.ImgVo;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IndexImgMapper {
    IndexImgMapper INSTANCE = Mappers.getMapper(IndexImgMapper.class);
    ImgVo toVo(IndexImg entity);
    List<ImgVo> toVo(List<IndexImg> entities);
}
