package practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import practice.dto.*;
import practice.result.Result;
import practice.service.CategoryService;
import practice.service.ChatService;
import practice.service.GroupService;
import practice.service.TaskService;
import practice.user.CustomUserDetails;
import practice.vo.CategoryVo;
import practice.vo.GroupVo;
import practice.vo.TaskVo;
import practice.vo.UserVo;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    ChatService chatService;

    @PostMapping
    public Result<GroupVo> addGroup(@AuthenticationPrincipal CustomUserDetails user, @RequestBody GroupDto dto) {
        GroupVo vo = groupService.addGroup(user.getUserId(), dto);
        return Result.success(vo);
    }

    @GetMapping
    public Result<List<GroupVo>> getGroups(@AuthenticationPrincipal CustomUserDetails user) {
        List<GroupVo> vos = groupService.getGroups(user.getUserId());
        return Result.success(vos);
    }

    @PostMapping("/{groupId}/users")
    public Result<UserVo> addNewUser(@PathVariable("groupId") Integer groupId, @RequestBody UserDto newUser) {
        UserVo vo = groupService.addNewUser(newUser.getId(), groupId);
        return Result.success(vo);
    }

    @GetMapping("/{groupId}/users")
    public Result<List<UserVo>> getUsers(@PathVariable("groupId") Integer groupId) {
        List<UserVo> vos = groupService.getUsers(groupId);
        return Result.success(vos);
    }

    @GetMapping("/{groupId}/categories")
    public Result<List<CategoryVo>> getCategories(@AuthenticationPrincipal CustomUserDetails user, @PathVariable("groupId") Integer groupId) {
        List<CategoryVo> vo = categoryService.getCategories(user.getUserId(), groupId);
        return Result.success(vo);
    }

    @PostMapping("/{groupId}/category")
    public Result<CategoryVo> addCategory(@PathVariable("groupId") Integer groupId, @RequestBody CategoryDto categoryDto) {
        CategoryVo vo = categoryService.addCategory(groupId, categoryDto.getName());
        return Result.success(vo);
    }

    @PostMapping("/{groupId}/category/{categoryId}/task")
    public Result<TaskVo> addTask(@PathVariable("groupId") Integer groupId, @PathVariable("categoryId") Integer categoryId, @RequestBody TaskDto dto) {
        TaskVo vo = taskService.addTask(groupId, categoryId, dto);
        return Result.success(vo);
    }

    @PutMapping("/{groupId}/task/{taskId}")
    public Result<TaskVo> modifyTask(@PathVariable("groupId") Integer groupId, @PathVariable("taskId") Integer taskId, @RequestBody TaskDto dto) {
        TaskVo vo = taskService.modifyTask(taskId, dto);
        return Result.success(vo);
    }

    @PutMapping("/{groupId}/task/{taskId}/status")
    public Result<TaskVo> updateTaskStatus(@PathVariable("groupId") Integer groupId, @PathVariable("taskId") Integer taskId) {
        TaskVo vo = taskService.updateTaskStatus(groupId, taskId);
        return Result.success(vo);
    }


    @GetMapping("/{groupId}/tasks")
    public Result<List<TaskVo>> getTasks(@PathVariable("groupId") Integer groupId, @AuthenticationPrincipal CustomUserDetails user) {
        List<TaskVo> vos = taskService.getTasks(user.getUserId(), groupId);
        return Result.success(vos);
    }

    @GetMapping("/{groupId}/task/{taskId}")
    public Result<TaskVo> getTask(@PathVariable("groupId") Integer groupId, @PathVariable("taskId") Integer taskId, @AuthenticationPrincipal CustomUserDetails user) {
        TaskVo vo = taskService.getTask(user.getUserId(), groupId, taskId);
        return Result.success(vo);
    }

    @DeleteMapping("/{groupId}/task/{taskId}")
    public Result<TaskVo> deleteTask(@PathVariable("groupId") Integer groupId, @PathVariable("taskId") Integer taskId, @AuthenticationPrincipal CustomUserDetails user) {
        taskService.deleteTask(user.getUserId(), groupId, taskId);
        return Result.success();
    }

    @GetMapping("/{groupId}/chat")
    public Result<List<ChatMessageDto>> getChat(@PathVariable("groupId") Integer groupId, @AuthenticationPrincipal CustomUserDetails user) {
        List<ChatMessageDto> messages = chatService.getChat(groupId, user.getUserId());
        return Result.success(messages);
    }
}
