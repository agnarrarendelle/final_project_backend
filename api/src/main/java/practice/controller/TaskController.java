package practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import practice.dto.TaskDto;
import practice.result.Result;
import practice.service.TaskService;
import practice.user.CustomUserDetails;
import practice.vo.TaskVo;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

}
