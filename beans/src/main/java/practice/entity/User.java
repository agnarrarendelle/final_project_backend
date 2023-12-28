package practice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    private String password;

    @Column
    private String nickname;

    @Column(name="realname")
    private String realName;

    @Column(name="user_img", columnDefinition="default 'img/default.png'")
    @NotNull
    private String userImg;

    @Column(name="user_mobile")
    private String userMobile;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="user_sex")
    private String userSex;

    @Column(name="user_birth")
    private Date userBirth;

    @Column(name="user_regtime", updatable = false)
    @CreationTimestamp
    private Timestamp userRegTime;

    @Column(name="user_modtime")
    @UpdateTimestamp
    private Timestamp userModTime;
}
