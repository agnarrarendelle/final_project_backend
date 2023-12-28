package practice.vo.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import practice.vo.ImgVo;
import practice.vo.productsku.ProductSkuVo;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductVo {
    private Integer productId;
    private String productName;
    private Integer soldNum;
    private List<ImgVo> productImages;
    private List<ProductSkuVo> productSkus;
}
