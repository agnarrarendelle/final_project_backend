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
import practice.exception.*;
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
    public TaskVo addTask(Integer groupId, Integer categoryId, TaskDto dto) {
        Category category = categoryRepository.findById(categoryId).get();

        Task newTask = Task.builder()
                .name(dto.getName())
                .priorityLevel(toTaskPriorityLevel(dto.getPriorityLevel()))
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
            throw new UserNotBelongingInGroupException(userId);
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

    @Override
    public TaskVo getTask(int userId, Integer groupId, Integer taskId) {
        if (!groupService.isUserInGroup(userId, groupId)) {
            throw new UserNotBelongingInGroupException(userId);
        }

        Task task = taskRepository.findById(taskId).orElseThrow(() -> {
            throw new TaskNotExistException(taskId);
        });

        return TaskVo
                .builder()
                .id(task.getId())
                .name(task.getName())
                .priorityLevel(task.getPriorityLevel().toString())
                .status((task.getStatus().toString()))
                .expiredAt(task.getExpiredAt())
                .build();
    }

    @Override
    @Transactional
    public TaskVo modifyTask(Integer taskId, TaskDto dto) {
        Task task = taskRepository.findByIdWithCategory(taskId);

        task.setName(dto.getName());
        task.setExpiredAt(new Timestamp(dto.getExpiredAt().getTime()));
        task.setPriorityLevel((toTaskPriorityLevel(dto.getPriorityLevel())));

        taskRepository.save(task);
        return TaskVo
                .builder()
                .id(task.getId())
                .name(task.getName())
                .priorityLevel(task.getPriorityLevel().toString())
                .status((task.getStatus().toString()))
                .expiredAt(task.getExpiredAt())
                .categoryName(task.getCategory().getName())
                .build();
    }

    @Override
    @Transactional
    public void deleteTask(int userId, Integer groupId, Integer taskId) {
        if (!groupService.isUserInGroup(userId, groupId)) {
            throw new UserNotBelongingInGroupException(userId);
        }
        taskRepository.deleteByIdAndGroupId(taskId, groupId);
    }

    @Override
    @Transactional
    public TaskVo updateTaskStatus(Integer groupId, Integer taskId) {
        Task task = taskRepository.findById(taskId).get();

        if (task.isExpired()) {
            throw new TaskStatusNotModifiableException(taskId);
        }

        if (task.getStatus() == Task.TaskStatus.Finished && task.isDue()) {
            throw new TaskStatusNotModifiableException(taskId);
        }

        Task.TaskStatus flippedStatus =
                task.getStatus() == Task.TaskStatus.InProgress ?
                        Task.TaskStatus.Finished :
                        Task.TaskStatus.InProgress;


        task.setStatus(flippedStatus);

        taskRepository.save(task);

        return TaskVo
                .builder()
                .id(task.getId())
                .name(task.getName())
                .priorityLevel(task.getPriorityLevel().toString())
                .status((task.getStatus().toString()))
                .expiredAt(task.getExpiredAt())
                .categoryName(task.getCategory().getName())
                .build();
    }

    @Override
    @Transactional
    public void expireTasks() {
        taskRepository.expireTasks();
    }

    private Task.TaskStatus toTaskStatus(String name) {
        try {
            return Task.TaskStatus.valueOf(name);
        } catch (IllegalArgumentException e) {
            throw new InvalidTaskStatusException(name);
        }
    }

    private Task.TaskPriorityLevel toTaskPriorityLevel(String name){
        try {
            return Task.TaskPriorityLevel.valueOf(name);
        } catch (IllegalArgumentException e) {
            throw new InvalidTaskPriorityLevelException(name);
        }
    }
}
