package practice.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "user_id")
    @NotNull
    private int userId;

    @Column(name = "untitled")
    private String productNames;

    @Column(name = "receiver_name")
    @NotNull
    private String receiverName;

    @Column(name = "receiver_mobile")
    @NotNull
    private String receiverMobile;

    @Column(name = "receiver_address")
    @NotNull
    private String receiverAddress;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "actual_amount")
    private Integer actualAmount;

    @Column(name = "pay_type")
    private Integer payType;

    @Column(name = "order_remark")
    private String orderRemark;

    @Column(name = "status")
    private String status;

    @Column(name = "delivery_type")
    private String deliveryType;

    @Column(name = "delivery_flow_id")
    private String deliveryFlowId;

    @Column(name = "order_freight")
    private BigDecimal orderFreight;

    @Column(name = "delete_status")
    private Integer deleteStatus;

    @Column(name = "create_time", updatable = false)
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    @Column(name = "pay_time")
    private Timestamp payTime;

    @Column(name = "delivery_time")
    private Timestamp deliveryTime;

    @Column(name = "flish_time")
    private Timestamp finishTime;

    @Column(name = "cancel_time")
    private Timestamp cancelTime;

    @Column(name = "close_type")
    private Integer closeType;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderItem> orderItems;
}
