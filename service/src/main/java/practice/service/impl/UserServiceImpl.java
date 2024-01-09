package practice.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.dto.UserDto;
import practice.entity.UserEntity;
import practice.exception.UserAlreadyExistException;
import practice.repository.UserRepository;
import practice.service.UserService;
import practice.user.CustomUserDetails;
import practice.utils.JwtUtils;
import practice.vo.UserVo;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    @Transactional
    public UserVo login(UserDto userDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDto.getName(), userDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String token = jwtUtils.createJwt(userDetails);

        return UserVo.builder()
                .token(token)
                .id(userDetails.getUserId())
                .name(userDetails.getUsername())
                .build();
    }

    @Override
    @Transactional
    public void register(UserDto userDto) {
        userRepository.findByNameOrEmail(userDto.getName(), userDto.getEmail()).ifPresent(u -> {
            throw new UserAlreadyExistException();
        });

        UserEntity newUser = UserEntity.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        userRepository.save(newUser);
    }

    @Override
    public List<UserVo> searchUsers(Integer groupId,String name) {
        List<UserEntity> users = userRepository.findByNameContainingAndNotInGroup(name, groupId);
        return users
                .stream()
                .map(u -> UserVo
                        .builder()
                        .id(u.getId())
                        .name(u.getName())
                        .build())
                .toList();
    }
}
