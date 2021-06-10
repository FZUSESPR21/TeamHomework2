package com.example.scoringsystem.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.scoringsystem.bean.JWTToken;
import com.example.scoringsystem.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import java.io.UnsupportedEncodingException;

@Slf4j
public class JWTCredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        log.info("进入自定义Matcher");
        JWTToken token = (JWTToken) authenticationToken;
        log.info("验证信息token:" + token);
        Object stored = authenticationInfo.getCredentials();
        String salt = stored.toString();
        log.info("验证信息：" + salt);
        log.info("验证信息:" + authenticationInfo.getPrincipals().getPrimaryPrincipal().toString());
        User user = (User) authenticationInfo.getPrincipals().getPrimaryPrincipal();
        log.info("正在验证的user" + user.toString());
        try {
            Algorithm algorithm = Algorithm.HMAC256(salt);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", user.getAccount())
                    .build();
            verifier.verify(token.getToken());
            return true;
        } catch (UnsupportedEncodingException | JWTVerificationException e) {
            log.error("Token Error:{}", e.getMessage());
        }

        return false;
    }
}
