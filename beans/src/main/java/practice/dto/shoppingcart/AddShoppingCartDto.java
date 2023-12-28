package practice.dto.shoppingcart;

import lombok.Data;

@Data
public class AddShoppingCartDto {
    private int cartNum;
    private int productId;
    private double productPrice;
    private int skuId;
    private String skuProps;
    private int userId;
}
