package practice.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.dto.TaskDto;
import practice.entity.Category;
import practice.entity.GroupEntity;
import practice.entity.Task;
import practice.exception.InvalidTaskPriorityLevelException;
import practice.exception.InvalidTaskStatusException;
import practice.repository.CategoryRepository;
import practice.repository.TaskRepository;
import practice.service.GroupService;
import practice.service.TaskService;
import practice.vo.TaskVo;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    GroupService groupService;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    @Transactional
    public TaskVo addTask( Integer groupId,Integer categoryId, TaskDto dto) {
        Task.TaskPriorityLevel priorityLevel;
        Task.TaskStatus status;

        try {
            priorityLevel = Task.TaskPriorityLevel.valueOf(dto.getPriorityLevel());
        } catch (IllegalArgumentException e) {
            throw new InvalidTaskPriorityLevelException(dto.getPriorityLevel());
        }

        Category category = categoryRepository.findById(categoryId).get();

        Task newTask = Task.builder()
                .name(dto.getName())
                .priorityLevel(priorityLevel)
                .expiredAt(new Timestamp(dto.getExpiredAt().getTime()))
                .group(entityManager.getReference(GroupEntity.class, groupId))
                .category(category)
                .build();

        taskRepository.save(newTask);
        return TaskVo
                .builder()
                .id(newTask.getId())
                .name(dto.getName())
                .status(dto.getStatus())
                .priorityLevel(dto.getPriorityLevel())
                .expiredAt(newTask.getExpiredAt())
                .categoryName(category.getName())
                .build();
    }

    @Override
    public List<TaskVo> getTasks(Integer userId, Integer groupId) {
        if (!groupService.isUserInGroup(userId, groupId)) {
            return List.of();
        }

        List<Task> tasks = taskRepository.findAllByGroupIdWithCategory(groupId);

        return tasks
                .stream()
                .map(t -> TaskVo
                        .builder()
                        .id(t.getId())
                        .name(t.getName())
                        .status(t.getStatus().toString())
                        .priorityLevel(t.getPriorityLevel().toString())
                        .expiredAt(t.getExpiredAt())
                        .categoryName(t.getCategory().getName())
                        .build())
                .toList();
    }
}
