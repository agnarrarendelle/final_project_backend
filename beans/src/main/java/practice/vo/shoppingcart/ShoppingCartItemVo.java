package practice.vo.shoppingcart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemVo {
    private String productName;
    private String productImg;
    private Integer productId;
    private String skuName;
    private String skuProps;
    private Integer skuId;
    private Integer originalPrice;
    private Integer sellPrice;
    private Integer cartNum;
    private Integer cartId;

    public ShoppingCartItemVo(
            String productName,
            String productImg,
            Integer productId,
            String skuName,
            String skuProps,
            Integer originalPrice,
            Integer sellPrice,
            Integer cartNum,
            Integer cartId
    ) {
        this.productId = productId;
        this.productName = productName;
        this.productImg = productImg;
        this.skuName = skuName;
        this.skuProps = skuProps;
        this.sellPrice = sellPrice;
        this.originalPrice = originalPrice;
        this.cartNum = cartNum;
        this.cartId = cartId;
    }
    public ShoppingCartItemVo(
            Integer productId,
            String productName,
            String productImg,
            String skuName,
            String skuProps,
            Integer skuId,
            Integer originalPrice,
            Integer sellPrice,
            Integer cartNum
    ) {
        this.productId = productId;
        this.productName = productName;
        this.productImg = productImg;
        this.skuName = skuName;
        this.skuProps = skuProps;
        this.skuId = skuId;
        this.sellPrice = sellPrice;
        this.originalPrice = originalPrice;
        this.cartNum = cartNum;
    }
}
