package com.example.scoringsystem.shiro;

import com.example.scoringsystem.bean.Permissions;
import com.example.scoringsystem.bean.Role;
import com.example.scoringsystem.bean.User;
import com.example.scoringsystem.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.util.StringUtils;

/**
 * @Description: 自定义认证
 * @Author: 曹鑫
 * @Date: 2021/4/12
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    /**
     * @Description: 权限配置类
     * @Param: [principalCollection]
     * @return: org.apache.shiro.authz.AuthorizationInfo
     * @Date: 2021/4/12
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        //用户名
//        String name=(String) principalCollection.getPrimaryPrincipal();
//        //查询用户
//        User user = loginService.getUserByName(name);
//        //添加角色和权限
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        for (Role role:user.getRoles())
//        {
//            simpleAuthorizationInfo.addRole(role.getRoleName());
//            for (Permissions permissions : role.getPermissions())
//            {
//                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
//            }
//        }
        //获取用户信息
        User subjectUser = (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (subjectUser == null || subjectUser.getUserName() == null) {
            System.out.println("获取的primaryprincipal为空");
        } else {
            User user = loginService.selRolesByAccount(subjectUser.getAccount());
            log.info("当前用户的角色"+user);
            //加入角色
            if (!CollectionUtils.isEmpty(user.getRoles())) {
                for (Role role : user.getRoles()) {
                    info.addRole(role.getRoleName());
                    if (!CollectionUtils.isEmpty(role.getPermissions())) {
                        for (Permissions permissions : role.getPermissions()) {
                            info.addStringPermission(permissions.getPermissionsName());
                        }
                    }
                }
            }
            log.info("当前验证信息"+info.getRoles()+info.getStringPermissions());
            return info;
        }
        return null;
    }

    /**
     * @Description: 认证配置类
     * @Param: [authenticationToken]
     * @return: org.apache.shiro.authc.AuthenticationInfo
     * @Date: 2021/4/12
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证方法");
        if (StringUtils.isEmpty(authenticationToken.getPrincipal().toString())) {
            return null;
        }
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //验证用户名是否存在
        User user = loginService.getUserByAccount(usernamePasswordToken.getUsername());
        log.info("customRealm数据库查询的user" + user + "usernamePasswordToken.username" + usernamePasswordToken.getUsername());
        if (user == null) {
            return null;
        }
        //验证密码是否正确
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), this.getName());
        return simpleAuthenticationInfo;
    }
}
