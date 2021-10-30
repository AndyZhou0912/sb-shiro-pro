package wnxy.wn44.ssp.service;

import wnxy.wn44.ssp.entity.User;

/**
 * @author Administrator
 */
public interface UserService {

    /**
     * 根据用户名查找完整用户信息，用在用户登录上！
     * @param userName
     * @return
     */
    public User findUserByName(String userName);
}
