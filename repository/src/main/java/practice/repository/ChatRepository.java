package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import practice.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    @Query("SELECT c FROM Chat c JOIN FETCH c.chatMessages WHERE c.groupId = :groupId")
    Chat findByIdWithChatHistory(@Param("groupId") Integer groupId);
}
