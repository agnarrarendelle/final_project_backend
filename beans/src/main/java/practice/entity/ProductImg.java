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
@Table(name = "product_img")
public class ProductImg {
    @Id
    @Column(length = 64)
    @NotNull
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @NotNull
    @ToString.Exclude
    private Product product;

    @Column(name = "url", length = 128)
    @NotNull
    private String url;

    @Column(name = "sort")
    @NotNull
    private int sort;

    @Column(name = "is_main")
    @NotNull
    private int isMain;

    @Column(name = "created_time", updatable = false)
    @CreationTimestamp
    private Timestamp createdTime;

    @Column(name = "updated_time")
    @UpdateTimestamp
    private Timestamp updatedTime;
}
