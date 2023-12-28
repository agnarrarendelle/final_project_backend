package practice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import practice.entity.ShoppingCart;
import practice.vo.shoppingcart.ShoppingCartItemVo;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShoppingCartMapper {
    ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

    @Mapping(target = "sellPrice", source = "entity.sku.sellPrice")
    ShoppingCartItemVo toShoppingCartItemVo(ShoppingCart entity);
    List<ShoppingCartItemVo> toShoppingCartItemVo(List<ShoppingCart> entities);

}
