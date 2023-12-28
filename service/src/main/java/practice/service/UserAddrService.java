package practice.service;

import practice.vo.useraddr.UserAddrVo;

import java.util.List;

public interface UserAddrService {
    List<UserAddrVo> listAddrsByUserId(int userId);
}
