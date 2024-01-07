package practice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Entity
@Table(name = "task")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@DynamicInsert

public class Task {
    public enum TaskStatus {
        InProgress("InProgress"),
        Finished("Finished"),
        Expired("Expired");
        private final String value;

        TaskStatus(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    public enum TaskPriorityLevel {
        High("High"),
        Medium("Medium"),
        Low("Low");

        private final String value;

        TaskPriorityLevel(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 128)
    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @ColumnDefault("''")
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority_level")
    @NotNull
    private TaskPriorityLevel priorityLevel;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "finished_at")
    private Timestamp finishedAt;

    @Column(name = "expired_at")
    @NotNull
    private Timestamp expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @NotNull
    private GroupEntity group;

    @Column(name="group_id", insertable=false, updatable=false)
    private Integer groupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;
}
