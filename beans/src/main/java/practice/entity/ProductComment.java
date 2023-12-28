package practice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_comments")
public class ProductComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comm_id")
    private int commId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "order_item_id")
    private String orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_anonymous")
    private int isAnonymous;

    @Column(name = "comm_type")
    private int commType;

    @Column(name = "comm_level")
    @NotNull
    private int commLevel;

    @Column(name = "comm_content")
    @NotNull
    private String commContent;

    @Column(name = "comm_imgs")
    private String commImages;

    @Column(name = "sepc_name")
    private Timestamp specName;

    @Column(name = "reply_status")
    private int replyStatus;

    @Column(name = "reply_content")
    private String replyContent;

    @Column(name = "reply_time")
    private Timestamp replyTime;

    @Column(name = "is_show")
    private int isShow;
}
