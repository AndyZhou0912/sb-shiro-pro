package wnxy.wn44.ssp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wnxy.wn44.ssp.entity.Role;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface RoleMapper {

    /**
     * RBAC是基于角色 ，用户 不直接对接 权限
     *      所以在用户登录后，需要根据用户来获取角色
     *      用户角色表中记录的是角色和用户的编号
     * @param userId
     * @return
     */
    public List<Role> selectRoleByUserId(@Param("userId") Integer userId);
}
