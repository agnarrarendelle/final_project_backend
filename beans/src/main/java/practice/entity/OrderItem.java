package practice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
public class OrderItem {
    @Id
    @Column(name = "item_id")
    private String itemId;

    @Column(name = "product_name")
    @NotNull
    private String productName;

    @Column(name = "product_img")
    @NotNull
    private String productImg;

    @ManyToOne
    @JoinColumn(name = "sku_id")
    @NotNull
    private ProductSku sku;

    @Column(name = "sku_id", insertable = false, updatable = false)
    private int skuId;

    @Column(name = "sku_name")
    @NotNull
    private String skuName;

    @Column(name = "product_price")
    @NotNull
    private BigDecimal productPrice;

    @Column(name = "buy_counts")
    @NotNull
    private int buyCounts;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "basket_date")
    private Timestamp basketDate;

    @Column(name = "buy_time", updatable = false)
    @CreationTimestamp
    private Timestamp buyTime;

    @Column(name = "is_comment")
    private Integer isComment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @NotNull
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @Column(name = "product_id", insertable = false, updatable = false)
    @NotNull
    private int productId;
}
