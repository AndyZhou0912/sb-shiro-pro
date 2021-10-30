package wnxy.wn44.ssp.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import wnxy.wn44.ssp.entity.Permission;
import wnxy.wn44.ssp.entity.Role;
import wnxy.wn44.ssp.entity.User;
import wnxy.wn44.ssp.service.UserService;

import java.util.Set;

/**
 *  只能做认证
 * @author Administrator
 */
public class UserRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(UserRealm.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
        认证的方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //-- 本身就是获取用户名的方式！！没必要强转成UsernamePasswordToken
        logger.info(token.getPrincipal().toString());
        logger.info(token.getCredentials().toString());
        //-- 获取用户名
        String userName =  token.getPrincipal().toString();
        //-- 获取完成的User对象
        User loginUser = userService.findUserByName(userName);
        //-- 判断下用户是否存在！
        if (loginUser == null){
            return null;
        }
        //-- 把User封装成AuthenticationInfo，交给Shiro来判断密码
        //Object principal,   用户名
        //Object credentials, 从数据库查询出来的密码
        //String realmName    realmName
        AuthenticationInfo info = new SimpleAuthenticationInfo(userName,loginUser.getPassword(),this.getName());
        return info;
    }

    /**
     * 授权的方法
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        User currentUser = userService.findUserByName(userName);

        //-- 构建授权器对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //-- 用户所具备的所有的角色
        Set<Role> roleSet = currentUser.getRoleSet();
        //-- 遍历角色
        for (Role role : roleSet) {
            //-- 把角色添加到info中
            info.addRole(role.getRoleName());
            //-- 取角色所关联的权限
            Set<Permission> permissions = role.getPermissions();
            //-- 遍历角色相关联的所有权限
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getPermissionsName());
            }
        }

        return info;
    }
}
