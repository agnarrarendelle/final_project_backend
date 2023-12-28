package practice.service;


import practice.vo.UserLoginVo;

public interface UserService {
    public void register(String username, String password);
    public UserLoginVo login(String username, String password);
}
