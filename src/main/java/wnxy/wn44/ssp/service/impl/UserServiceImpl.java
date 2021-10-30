package wnxy.wn44.ssp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wnxy.wn44.ssp.entity.User;
import wnxy.wn44.ssp.mapper.UserMapper;
import wnxy.wn44.ssp.service.UserService;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findUserByName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }
}
