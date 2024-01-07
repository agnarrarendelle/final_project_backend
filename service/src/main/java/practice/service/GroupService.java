package practice.service;

import practice.dto.GroupDto;
import practice.vo.GroupVo;
import practice.vo.UserVo;

import java.util.List;

public interface GroupService {
    public GroupVo addGroup(Integer userID, GroupDto dto);

    List<GroupVo> getGroups(Integer userId);

    boolean isUserInGroup(Integer userId, Integer groupId);

    UserVo addNewUser(Integer userId, Integer groupId);

    List<UserVo> getUsers(Integer groupId);
}
