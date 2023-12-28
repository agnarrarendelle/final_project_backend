package practice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Table;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_addr")
public class UserAddr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addr_id")
    private int addressId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "receiver_name")
    @NotNull
    private String receiverName;

    @Column(name = "receiver_mobile")
    @NotNull
    private String receiverMobile;

    @Column(name = "province")
    @NotNull
    private String province;

    @Column(name = "city")
    @NotNull
    private String city;

    @Column(name = "area")
    @NotNull
    private String area;

    @Column(name = "addr")
    @NotNull
    private String addr;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "status")
    private int status;

    @Column(name = "common_addr")
    private int commonAddr;

    @Column(name = "create_time", updatable = false)
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
