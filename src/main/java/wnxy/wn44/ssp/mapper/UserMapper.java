package wnxy.wn44.ssp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wnxy.wn44.ssp.entity.User;

/**
 * @author Administrator
 */
@Mapper
public interface UserMapper {

    /**
     * 业务层执行登录行为时，根据用户名密码查询数据库中是否有相关用户
     * 那在持久层做的事情就是根据用户名获取完整的User对象，密码的验证
     * 交给Shiro
     *
     * @param userName
     * @return
     * Tips： 用户名必须唯一
     */
    User selectUserByUserName(@Param("userName")String userName);
}
