package practice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "index_img")
public class IndexImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Integer imgId;

    @Column(name = "img_url")
    @NotNull
    private String imgUrl;

    @Column(name = "img_bg_color")
    private String imgBgColor;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "index_type")
    @NotNull
    private int indexType;

    @Column(name = "seq")
    @NotNull
    private int seq;

    @Column(name = "status")
    @NotNull
    private int status;

    @Column(name = "create_time", updatable = false)
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;
}
