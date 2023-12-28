package practice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import practice.vo.shoppingcart.ShoppingCartItemVo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_cart")
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "findAllByUserIdWithProductSkuAndProductMapping",
                classes = {
                        @ConstructorResult(
                                targetClass = ShoppingCartItemVo.class,
                                columns = {
                                        @ColumnResult(name = "productName", type = String.class),
                                        @ColumnResult(name = "productImg", type = String.class),
                                        @ColumnResult(name = "productId", type = Integer.class),
                                        @ColumnResult(name = "skuName", type = String.class),
                                        @ColumnResult(name = "skuProps", type = String.class),
                                        @ColumnResult(name = "originalPrice", type = Integer.class),
                                        @ColumnResult(name = "sellPrice", type = Integer.class),
                                        @ColumnResult(name = "cartNum", type = Integer.class),
                                        @ColumnResult(name = "cartId", type = Integer.class),

                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "findAllByUserIdAndCartIdsWithProductSkuAndProductMapping",
                classes = {
                        @ConstructorResult(
                                targetClass = ShoppingCartItemVo.class,
                                columns = {
                                        @ColumnResult(name = "productId", type = Integer .class),
                                        @ColumnResult(name = "productName", type = String.class),
                                        @ColumnResult(name = "productImg", type = String.class),
                                        @ColumnResult(name = "skuName", type = String.class),
                                        @ColumnResult(name = "skuProps", type = String.class),
                                        @ColumnResult(name = "skuId", type = Integer.class),
                                        @ColumnResult(name = "originalPrice", type = Integer.class),
                                        @ColumnResult(name = "sellPrice", type = Integer.class),
                                        @ColumnResult(name = "cartNum", type = Integer.class),
                                }
                        )
                }
        )
})

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "ShoppingCart.findAllByUserIdWithProductSkuAndProduct",
                query = "SELECT " +
                        "c.cart_id AS cartId, " +
                        "c.product_id AS productId, " +
                        "c.cart_num AS cartNum, " +
                        "c.sku_props AS skuProps, " +
                        "p.product_name AS productName, " +
                        "i.url AS productImg, " +
                        "s.original_price AS originalPrice, " +
                        "s.sell_price AS sellPrice, " +
                        "s.sku_name AS skuName " +
                        "FROM shopping_cart c " +
                        "INNER JOIN product p ON c.product_id = p.product_id " +
                        "INNER JOIN product_img i ON i.product_id = p.product_id " +
                        "INNER JOIN product_sku s ON c.sku_id = s.sku_id " +
                        "WHERE c.user_id = :userId AND i.is_main = 1",
                resultSetMapping = "findAllByUserIdWithProductSkuAndProductMapping",
                resultClass = ShoppingCartItemVo.class
        ),
        @NamedNativeQuery(
                name = "ShoppingCart.findAllByUserIdAndCartIdsWithProductSkuAndProduct",
                query = "SELECT " +
                        "c.cart_num AS cartNum, " +
                        "c.sku_props AS skuProps, " +
                        "p.product_id AS productId, " +
                        "p.product_name AS productName, " +
                        "i.url AS productImg, " +
                        "s.sell_price AS sellPrice, " +
                        "s.sku_name AS skuName, " +
                        "s.sku_id AS skuId, " +
                        "s.original_price AS originalPrice " +
                        "FROM shopping_cart c " +
                        "INNER JOIN product p ON c.product_id = p.product_id " +
                        "INNER JOIN product_img i ON i.product_id = p.product_id " +
                        "INNER JOIN product_sku s ON c.sku_id = s.sku_id " +
                        "WHERE c.user_id = :userId AND i.is_main = 1 AND c.cart_id IN :cartIds",
                resultSetMapping = "findAllByUserIdAndCartIdsWithProductSkuAndProductMapping",
                resultClass = ShoppingCartItemVo.class
        )
})

public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    @NotNull
    private ProductSku sku;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "cart_num")
    private int cartNum;

    @Column(name = "cart_time")
    @UpdateTimestamp
    private Timestamp cartTime;

    @Column(name = "product_price")
    private double productPrice;

    @Column(name = "sku_props")
    private String skuProps;
}
