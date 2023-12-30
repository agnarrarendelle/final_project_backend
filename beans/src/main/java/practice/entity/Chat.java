package practice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;
}
