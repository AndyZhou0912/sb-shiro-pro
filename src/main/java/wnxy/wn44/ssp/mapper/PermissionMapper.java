package wnxy.wn44.ssp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wnxy.wn44.ssp.entity.Permission;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface PermissionMapper {

    /**
     * 用户登录后，获取用户编号，根据用户编号获取角色编号，再根据角色编号
     * 到角色权限表中查询出相应的权限
     * @param roleId
     * @return
     */

    public List<Permission> selectPermissionByRoleId(@Param("roleId") Integer roleId);
}
