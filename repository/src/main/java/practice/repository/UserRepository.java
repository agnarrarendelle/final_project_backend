package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import practice.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByName(String name);
    Optional<UserEntity> findByNameOrEmail(String name, String email);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.groups WHERE u.id = :id")
    Optional<UserEntity> findByIdWithGroups(@Param("id") Integer id);

    List<UserEntity> findByNameContaining(String name);

    @Query(nativeQuery = true, value =
            "SELECT u.* " +
            "FROM user u " +
            "WHERE UPPER(u.name) LIKE CONCAT('%',UPPER(:name),'%') " +
            "AND u.id NOT IN ( " +
            "    SELECT ug.user_id " +
            "    FROM user_group ug " +
            "    WHERE ug.group_id = :groupId " +
            ");"
            )
    List<UserEntity> findByNameContainingAndNotInGroup(@Param("name") String name, @Param("groupId") Integer groupId);
}
