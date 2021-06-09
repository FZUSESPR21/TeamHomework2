package com.example.scoringsystem.shiro;

import com.example.scoringsystem.bean.JWTToken;
import com.example.scoringsystem.bean.User;
import com.example.scoringsystem.service.UserService;
import com.example.scoringsystem.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class JWTShiroRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    /**
     * @Description: 使用自定义的Matcher
     * @Param: []
     * @return:
     * @Date: 2021/4/30
     */
    public JWTShiroRealm() {
        this.setCredentialsMatcher(new JWTCredentialsMatcher());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * @Description: 获取salt值给shiro有shiro进行认证。
     * @Param: [authenticationToken]
     * @return: org.apache.shiro.authc.AuthenticationInfo
     * @Date: 2021/4/30
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("进入jwtrealm的认证方法");
        JWTToken jwtToken = (JWTToken) authenticationToken;
        String token = jwtToken.getToken();
        User user = new User();
        log.info("进入jwtrealm的username" + JwtUtils.getUsername(token));
        user.setAccount(JwtUtils.getUsername(token));
        user = userService.getJwtTokenInfo(user);
        log.info("验证过程数据库查询的user" + user.toString());
        if (user.getAccount() == null) {
            throw new AuthenticationException("token过期，请重新登录");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getTokenSalt(), this.getName());
        return authenticationInfo;
    }

    /**
     * @Description: 限定此Realm只支持我们自定义的JWT Token
     * @Param: [token]
     * @return: boolean
     * @Date: 2021/4/30
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
}
