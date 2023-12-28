package practice.entity;

import lombok.*;
import org.hibernate.annotations.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(name = "product_name")
    @NotNull
    private String productName;

    @Column(name = "sold_num")
    @NotNull
    private int soldNum;

    @Column(name = "product_status")
    @NotNull
    private int productStatus;

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "create_time", updatable = false)
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_category_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Category rootCategory;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    private List<ProductImg> productImages;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    private List<ProductSku> productSkus;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval=true)
    @ToString.Exclude
    private ProductParam productParams;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    private List<ProductComment> productComments;
}
