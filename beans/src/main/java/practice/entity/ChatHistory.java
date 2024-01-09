package practice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "chat_history")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ChatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content", length = 256, nullable = false)
    private String content;

    @Column(name = "sentAt")
    @CreationTimestamp
    private Timestamp sentAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name="user_id", insertable=false, updatable=false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;
}
