package com.example.scoringsystem.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.example.scoringsystem.filter.JwtAuthFilter;
import com.example.scoringsystem.shiro.CustomRealm;
import com.example.scoringsystem.shiro.JWTCredentialsMatcher;
import com.example.scoringsystem.shiro.JWTShiroRealm;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {


    /**
     * 注册shiro的Filter，拦截请求
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(DefaultWebSecurityManager securityManager) throws Exception {
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
        filterRegistration.setFilter((Filter) shiroFilterFactoryBean(securityManager).getObject());
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);

        return filterRegistration;
    }

    /**
     * 初始化Authenticator
     */
    @Bean
    public Authenticator authenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        //设置两个Realm，一个用于用户登录验证和访问权限获取；一个用于jwt token的认证
        authenticator.setRealms(Arrays.asList(jwtShiroRealm(), myShiroRealm()));
        //设置多个realm认证策略，一个成功即跳过其它的
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
     * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session，如果要完全禁用，要配合下面的noSessionCreation的Filter来实现
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    /**
     * 用于用户名密码登录时认证的realm
     */
    @Bean
    public CustomRealm myShiroRealm() {
        CustomRealm customerRealm = new CustomRealm();
        //设置hashed凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置md5加密
        credentialsMatcher.setHashAlgorithmName("md5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(credentialsMatcher);
        return customerRealm;
    }

    /**
     * 用于JWT token认证的realm
     */
    @Bean("jwtRealm")
    public Realm jwtShiroRealm() {
        JWTShiroRealm myShiroRealm = new JWTShiroRealm();
//        //设置hashed凭证匹配器
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        //设置md5加密
//        credentialsMatcher.setHashAlgorithmName("md5");
//        //设置散列次数
//        credentialsMatcher.setHashIterations(1024);
        myShiroRealm.setCredentialsMatcher(new JWTCredentialsMatcher());
        return myShiroRealm;
    }

//    //将自己的验证方式加入容器
//    @Bean
//    public CustomRealm myShiroRealm() {
//        CustomRealm customerRealm = new CustomRealm();
//        //设置hashed凭证匹配器
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        //设置md5加密
//        credentialsMatcher.setHashAlgorithmName("md5");
//        //设置散列次数
//        credentialsMatcher.setHashIterations(1024);
//        customerRealm.setCredentialsMatcher(credentialsMatcher);
//        return customerRealm;
//    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(Arrays.asList(myShiroRealm(), jwtShiroRealm()));
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new LinkedHashMap<>();
        //登出
        map.put("/logout", "logout");

        //特殊权限
        map.put("/score/blogwork/showlist","roles[admin]");
        map.put("/pair/import","roles[admin]");
        map.put("/student/export","roles[admin]");
        map.put("/details/import","roles[admin]");
        map.put("/student/import","roles[teacher]");
        map.put("/student/import","roles[teacher]");

        //不需要验证
        //需要perms[user:add]权限
        map.put("/user/add", "perms[user:add]");
        //登录页面放行
        map.put("/login.html","anon");
//        map.put("/student/scoreinquiry.html","anon");
        map.put("/student/*","anon");
        map.put("/teacher/*", "anon");
        map.put("/student-page/*", "anon");
        map.put("/performanceManagement/*","anon");
        map.put("/assigement/*","anon");
        map.put("/jobManagemant/*","anon");
        //静态资源放行
        map.put("/css/*", "anon");
//        map.put("/*.html","anon");
        map.put("*.js","anon");
        map.put("/bootstrap-4.6.0-dist/**", "anon");
        map.put("/tableExport.jquery.plugin-master/**","anon");
//        map.put("/bootstrap-table/**", "anon");
        //flag
        map.put("/bootstrap-table/dist/*","anon");
//        map.put("/css/**", "anon");
        map.put("/editor.md-master/**", "anon");
        map.put("/editormd/**", "anon");
        map.put("/jquery/**", "anon");
        map.put("/js/**", "anon");
        map.put("/layui/**", "anon");

        //验证码放行
        map.put("/captcha","anon");
        //对所有用户认证
//        map.put("/**", "anon");
        map.put("/**", "authc");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index");

        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");
        LinkedHashMap<String, Filter> filtsMap = new LinkedHashMap<>();
        filtsMap.put("authc", new JwtAuthFilter());
        shiroFilterFactoryBean.setFilters(filtsMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //注入权限管理
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    //整合shiroDialect
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }
}