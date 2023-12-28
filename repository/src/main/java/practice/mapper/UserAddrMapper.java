package practice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import practice.entity.UserAddr;
import practice.vo.useraddr.UserAddrVo;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAddrMapper {
    UserAddrMapper INSTANCE = Mappers.getMapper(UserAddrMapper.class);

    UserAddrVo toUserAddrVo(UserAddr entity);
    List<UserAddrVo> toUserAddrVo(List<UserAddr> entities);
}
