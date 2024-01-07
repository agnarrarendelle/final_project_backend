package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import practice.entity.GroupEntity;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {
    Optional<GroupEntity> findByName(String name);

    @Query("SELECT g FROM GroupEntity g LEFT JOIN FETCH g.users WHERE g.id = :id")
    Optional<GroupEntity> findByIdWithUsers(@Param("id") Integer id);
}
