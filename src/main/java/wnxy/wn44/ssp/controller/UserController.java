package wnxy.wn44.ssp.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wnxy.wn44.ssp.entity.User;
import wnxy.wn44.ssp.service.UserService;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 提供日志记录器，尽量用它代替 System.out.println();
     */
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String doUserLogin(User user){
        //-- 获取当前登录用户
        Subject subject = SecurityUtils.getSubject();

        //-- 判断这个用户是否有登录
        if (!subject.isAuthenticated()){
            //-- 把用户名和密码封装成Token
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
            logger.info("user" + user.toString());
            logger.info("token"+token.hashCode());
            //-- 进行认证（进行登录）
            //                                                 AuthenticationToken
            //public interface HostAuthenticationToken extends AuthenticationToken
            //public class UsernamePasswordToken implements HostAuthenticationToken, RememberMeAuthenticationToken {
            try{
                //-- 执行login 就要取找Realm来查询数据库中的数据！！
                //-- 找到UserRealm 中的  doGetAuthenticationInfo，该方法的参数是：AuthenticationToken
                subject.login(token);
                logger.info("认证通过！");
            }catch (AuthenticationException e){
                logger.info("认证异常:" + e.getLocalizedMessage());
                return "error";
            }
        }
        return "index";
    }


    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @GetMapping("/toIndex")
    public String toIndex() {
        return "index";
    }

    /**
     * RequiresPermissions 是Shiro提供的注解，用于表示需要某个权限！！
     * @return
     */
    @RequiresPermissions("user:update")
    @GetMapping("/user/update")
    public String update() {
        return "update";
    }

    @RequiresPermissions("user:add")
    @GetMapping("/user/add")
    public String add() {
        return "add";
    }

    @RequiresPermissions("user:select")
    @GetMapping("/user/select")
    public String select() {
        return "update";
    }

    @RequiresPermissions("user:del")
    @GetMapping("/user/del")
    public String del() {
        return "add";
    }

    @GetMapping("/unauth")
    public String error() {
        return "401";
    }
}
