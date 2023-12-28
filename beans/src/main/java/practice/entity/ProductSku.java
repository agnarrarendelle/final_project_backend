package practice.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_sku")
public class ProductSku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int skuId;

    @Column(name = "product_id")
    @NotNull
    private int productId;

    @Column(name = "sku_name", length = 32)
    @NotNull
    private String skuName;

    @Column(name = "sku_img", length = 32)
    private String skuImg;

    @Column(name = "untitled", length = 400)
    private String untitled;

    @Column(name = "original_price")
    @NotNull
    private int originalPrice;

    @Column(name = "sell_price")
    @NotNull
    private int sellPrice;

    @Column(name = "discounts")
    @NotNull
    private double discounts;

    @Column(name = "stock")
    @NotNull
    private int stock;

    @Column(name = "create_time", updatable = false)
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Product product;
}
