package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import practice.entity.UserGroup;

import java.util.Optional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {

    @Query("SELECT ug FROM UserGroup ug WHERE ug.groupId = :groupId AND ug.userId = :userId")
    Optional<UserGroup> findByUserIdAndGroupId(@Param("groupId") Integer groupId, @Param("userId") Integer userId);
}
