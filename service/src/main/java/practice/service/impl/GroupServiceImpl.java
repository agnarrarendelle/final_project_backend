package practice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.dto.GroupDto;
import practice.entity.Chat;
import practice.entity.GroupEntity;
import practice.entity.UserEntity;
import practice.exception.GroupNameTakenException;
import practice.repository.ChatRepository;
import practice.repository.GroupRepository;
import practice.repository.UserRepository;
import practice.service.GroupService;
import practice.vo.GroupVo;
import practice.vo.UserVo;

import java.util.List;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatRepository chatRepository;

    @Override
    @Transactional
    public GroupVo addGroup(Integer userId, GroupDto dto) {
        groupRepository.findByName(dto.getName()).ifPresent(g -> {
            throw new GroupNameTakenException(g.getName());
        });

        UserEntity user = userRepository.findById(userId).get();

        GroupEntity newGroup = GroupEntity.builder()
                .name(dto.getName())
                .users(Set.of(user))
                .build();

        groupRepository.save(newGroup);

        Chat chat = Chat.builder().group(newGroup).build();

        chatRepository.save(chat);

        return GroupVo
                .builder()
                .id(newGroup.getId())
                .name(dto.getName())
                .build();
    }

    @Override
    public List<GroupVo> getGroups(Integer userId) {
        UserEntity user = userRepository.findByIdWithGroups(userId).get();

        return user.getGroups()
                .stream()
                .map(g -> GroupVo
                        .builder()
                        .id(g.getId())
                        .name(g.getName())
                        .build())
                .toList();
    }

    @Override
    public boolean isUserInGroup(Integer userId, Integer groupId) {
        UserEntity user = userRepository.findById(userId).get();
        Set<UserEntity> groupUsers = groupRepository.findByIdWithUsers(groupId).get().getUsers();

        return groupUsers.contains(user);
    }

    @Override
    public UserVo addNewUser(Integer userId, Integer groupId) {
        GroupEntity group = groupRepository.findByIdWithUsers(groupId).get();
        UserEntity newUser = userRepository.findById(userId).get();

        group.getUsers().add(newUser);

        groupRepository.save(group);
        return UserVo.builder().id(newUser.getId()).name(newUser.getName()).build();
    }

    @Override
    public List<UserVo> getUsers(Integer groupId) {
        GroupEntity group = groupRepository.findByIdWithUsers(groupId).get();

        return group.getUsers()
                .stream()
                .map(u-> UserVo
                        .builder()
                        .id(u.getId())
                        .name(u.getName())
                        .build())
                .toList();
    }
}