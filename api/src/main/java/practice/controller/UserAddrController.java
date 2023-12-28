package practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.result.Result;
import practice.service.UserAddrService;
import practice.user.CustomUserDetails;
import practice.vo.useraddr.UserAddrVo;

import java.util.List;

@RestController
@RequestMapping("/useraddr")
public class UserAddrController {
    @Autowired
    UserAddrService userAddrService;

    @GetMapping("/list")
    public Result<List<UserAddrVo>> listAddrs(@AuthenticationPrincipal CustomUserDetails user){
        List<UserAddrVo> vos = userAddrService.listAddrsByUserId(user.getUserId());
        return Result.success(vos);
    }

}
