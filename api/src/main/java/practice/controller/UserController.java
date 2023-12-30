package practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;//package practice.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.result.Result;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import practice.dto.user.RegisterLoginDto;
//import practice.result.Result;
//import practice.service.UserService;
//import practice.vo.UserLoginVo;
//
//import javax.validation.Valid;
//import java.security.PublicKey;
//
@RestController
@RequestMapping("/user")
public class UserController {

//    @Autowired
//    UserService userService;
//
//    @PostMapping("/register")
//    public Result<Void> register(@RequestBody @Valid RegisterLoginDto dto){
//        userService.register(dto.getUsername(), dto.getPassword());
//        return Result.success();
////    }
    @PostMapping("/login")
    public Result<Void> login(){
        return Result.success();
    }

//    @GetMapping("/check")
//    public Result<Void> userTokenCheck(){
//        return Result.success();
//    }

}
