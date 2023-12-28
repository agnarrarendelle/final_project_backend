package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.entity.UserAddr;

import java.util.List;

public interface UserAddrRepository extends JpaRepository<UserAddr, Integer> {
    List<UserAddr> findAllByUserUserId(int userId);
}
