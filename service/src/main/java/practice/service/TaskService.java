package practice.service;

import practice.dto.TaskDto;
import practice.vo.TaskVo;

import java.util.List;

public interface TaskService {
    TaskVo addTask(Integer groupId, Integer categoryId, TaskDto dto);

    List<TaskVo> getTasks(Integer userId, Integer groupId);

    TaskVo getTask(int userId, Integer groupId, Integer taskId);

    TaskVo modifyTask(Integer taskId, TaskDto dto);

    void deleteTask(int userId, Integer groupId, Integer taskId);

    TaskVo updateTaskStatus(Integer groupId, Integer taskId);

    void expireTasks();

    void deleteExpiredTasks();
}
