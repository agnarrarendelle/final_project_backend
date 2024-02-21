package practice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_group")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="group_id", insertable=false, updatable=false)
    private Integer groupId;

    @Column(name="user_id", insertable=false, updatable=false)
    private Integer userId;
}
