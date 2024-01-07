package practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice.dto.UserDto;
import practice.result.Result;
import practice.service.UserService;
import practice.vo.UserVo;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Result<Void> register(@RequestBody UserDto dto){
        userService.register(dto);
        return Result.success();
    }
    @PostMapping("/login")
    public Result<UserVo> login(@RequestBody UserDto dto){
        UserVo vo = userService.login(dto);
        return Result.success(vo);
    }

    @GetMapping
    public Result<List<UserVo>> searchUsers(@RequestParam Integer groupId,@RequestParam String name){
        List<UserVo> vos = userService.searchUsers(groupId, name);
        return Result.success(vos);
    }

    @PutMapping
    public Result<Void> checkLogin(){
        return Result.success();
    }
}
