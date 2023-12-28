package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT DISTINCT c FROM Category c " +
            "LEFT JOIN FETCH c.childCategories " +
            "WHERE c.categoryLevel = :categoryLevel"
    )
    List<Category> findAllWithChildCategories(@Param("categoryLevel") int categoryLevel);

    @Query("SELECT DISTINCT c FROM Category c " +
            "LEFT JOIN FETCH c.allChildProducts " +
            "WHERE c.categoryLevel = :categoryLevel"
    )
    List<Category> findAllWithAllChildProducts(@Param("categoryLevel") int categoryLevel);
}
