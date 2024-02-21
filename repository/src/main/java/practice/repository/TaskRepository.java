package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import practice.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT t FROM Task t JOIN FETCH t.category WHERE t.groupId = :groupId AND t.status != 'Expired' ")
    List<Task> findAllByGroupIdWithCategory(@Param("groupId") Integer groupId);

    @Query("SELECT t FROM Task t JOIN FETCH t.category WHERE t.id = :taskId")
    Task findByIdWithCategory(@Param("taskId") Integer taskId);

    void deleteByIdAndGroupId(Integer id, Integer groupId);

}
