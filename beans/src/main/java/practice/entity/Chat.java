package practice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(name="group_id", insertable=false, updatable=false)
    private Integer groupId;

    @OneToMany(mappedBy = "chat", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    private List<ChatHistory> chatMessages;
}
