package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByName(String name);
}
