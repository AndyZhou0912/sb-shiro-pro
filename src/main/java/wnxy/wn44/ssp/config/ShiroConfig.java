package wnxy.wn44.ssp.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wnxy.wn44.ssp.realm.UserRealm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置ShiroDialect，用于Shiro和thymeleaf标签配合使用
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /**
     * 将自己的验证方式加入容器
     * @return
     */
    @Bean(name = "userRealm")
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }



    /**
     * 权限管理，配置主要是Realm的管理认证
     *  注入有三个注解：
     *     @Autowired    根据类型来注入！
     *              假设有接口为UserService ，但是有实现类：UserServiceImpl1 和 UserServiceImpl2
     *              请问用Autowired会注入哪一个？
     *     @Qualifier    根据名称来注入Bean，一般用于解决 再IOC容器中一个类型的实例有多个的情况！！
     *
     *     @Resource
     *              不是Spring的注解，而是JDK自带的注解！功能和Qualifier类似
     *
     *
     *              public interface WebSecurityManager extends SecurityManager {
     *              public class DefaultWebSecurityManager extends DefaultSecurityManager implements WebSecurityManager {
     *
     * @param userRealm
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("userRealm")UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new LinkedHashMap<>();

        //登录  没有认证取登录页面
        shiroFilterFactoryBean.setLoginUrl("/user/toLogin");
        //首页   认证成功去首页面
        shiroFilterFactoryBean.setSuccessUrl("/user/toIndex");
//        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/unauth");
        //        过滤器种类
        //        1.anon 无需认证
        //        2.authc 必须认证
        //        3.user 记住我可以访问
        //        4.perms 授予权限才可以访问
        //        5.role 授予角色才可以访问
        //        6.logout登出
        //-- 配置其它过滤规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //登出
        map.put("/logout", "logout");

        //-- 去首页
        map.put("/user/toIndex","anon");
        //-- 去登录页面
        map.put("/user/toLogin","anon");
        //-- 登录页面提交数据到Controller的登录请你去
        map.put("/user/login","anon");

        //授予权限,让div在页面显示
        map.put("/user/add","perms[user:add]");
        map.put("/user/update","perms[user:update]");
        map.put("/user/select","perms[user:select]");
        map.put("/user/del","perms[user:del]");

        //-- 第一匹配优先！！
        //对所有用户认证
        map.put("/**", "authc");

        return shiroFilterFactoryBean;
    }
}
