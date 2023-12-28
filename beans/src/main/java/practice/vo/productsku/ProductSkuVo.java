package practice.vo.productsku;

import lombok.Data;

@Data
public class ProductSkuVo {
    private Integer skuId;
    private Integer sellPrice;
    private Integer originalPrice;
    private Integer stock;
    private Double discounts;
    private String untitled;
    private String skuImg;
    private String skuName;
}
