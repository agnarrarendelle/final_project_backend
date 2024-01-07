package practice.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import practice.dto.UserDto;
import practice.vo.UserVo;

import java.util.List;

public interface UserService {
    public UserVo login(UserDto userDto);

    public void register(UserDto userDto);

    List<UserVo> searchUsers(Integer groupId, String name);
}