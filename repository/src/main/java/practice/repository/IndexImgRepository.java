package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import practice.entity.IndexImg;

import java.util.List;

@Repository
public interface IndexImgRepository extends JpaRepository<IndexImg, Integer> {
    @Query("SELECT i FROM IndexImg i WHERE i.status = 1 ORDER BY i.seq")
    List<IndexImg> findActiveOnesAndSortedBySeq();
}
