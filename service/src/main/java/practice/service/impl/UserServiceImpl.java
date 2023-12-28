package practice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.entity.User;
import practice.exception.UsernameDuplicateException;
import practice.repository.UserRepository;
import practice.service.UserService;
import practice.user.CustomUserDetails;
import practice.utils.JwtUtils;
import practice.vo.UserLoginVo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    @Transactional
    public void register(String username, String password) {
        if(userRepository.existsByUsername(username)){
            throw new UsernameDuplicateException(username);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setUserImg("img/default.png");

        userRepository.save(user);
    }

    @Override
    public UserLoginVo login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        String token = jwtUtils.createJwt(userDetails, user.getUserId());

        return UserLoginVo.builder()
                .token(token)
                .userId(user.getUserId())
                .userImg(user.getUserImg())
                .username(user.getUsername())
                .build();
    }
}
