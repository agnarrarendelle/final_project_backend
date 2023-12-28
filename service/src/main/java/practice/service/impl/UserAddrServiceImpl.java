package practice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.entity.UserAddr;
import practice.mapper.UserAddrMapper;
import practice.repository.UserAddrRepository;
import practice.service.UserAddrService;
import practice.vo.useraddr.UserAddrVo;

import java.util.List;

@Service
public class UserAddrServiceImpl implements UserAddrService {

    @Autowired
    UserAddrRepository userAddrRepository;

    @Autowired
    UserAddrMapper userAddrMapper;
    @Override
    public List<UserAddrVo> listAddrsByUserId(int userId) {
        List<UserAddr> addrs = userAddrRepository.findAllByUserUserId(userId);
        return userAddrMapper.toUserAddrVo(addrs);
    }
}
