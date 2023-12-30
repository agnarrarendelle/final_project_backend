package practice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "\"group\"")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 128)
    private String name;

    @ManyToMany(mappedBy = "groups")
    private Set<UserEntity> users;
}
