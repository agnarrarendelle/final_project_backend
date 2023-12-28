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
@Table(name = "product_params")
public class ProductParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paramId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @NotNull
    @ToString.Exclude
    private Product product;

    @NotNull
    @Column(name = "product_place")
    private String productPlace;

    @NotNull
    @Column(name = "foot_period")
    private String footPeriod;

    @NotNull
    @Column(name = "brand")
    private String brand;

    @NotNull
    @Column(name = "factory_name")
    private String factoryName;

    @NotNull
    @Column(name = "factory_address")
    private String factoryAddress;

    @NotNull
    @Column(name = "packaging_method")
    private String packagingMethod;

    @NotNull
    @Column(name = "weight")
    private String weight;

    @NotNull
    @Column(name = "storage_method")
    private String storageMethod;

    @NotNull
    @Column(name = "eat_method")
    private String eatMethod;

    @Column(name = "create_time", updatable = false)
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;
}
