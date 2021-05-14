package com.example.scoring_system.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 参考SpringBoot中使用Shiro和JWT做认证和鉴权https://www.jianshu.com/p/0b1131be7ace
 * @Author: 曹鑫
 * @Date: 2021/4/29
 */
public class JwtUtils {

    /**
     * @Description: 获得token中的信息无需secret解密也能获得
     * @Param: [token]
     * @return: java.util.Date
     * @Date: 2021/4/29
     */
    public static Date getIssuedAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuedAt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * @Description: 获得token中的信息无需secret解密也能获得
     * @Param: [token]
     * @return: java.lang.String
     * @Date: 2021/4/29
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * @Description: 生成签名, expireTime后过期
     * @Param: [username, salt, time]
     * @return: java.lang.String
     * @Date: 2021/4/29
     */
    public static String sign(String username, String salt, long time) {
        try {
            Date date = new Date(System.currentTimeMillis() + time * 1000);
            Algorithm algorithm = Algorithm.HMAC256(salt);
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(date)
                    .withIssuedAt(new Date())
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * @Description: token是否过期
     * @Param: [token]
     * @return: boolean
     * @Date: 2021/4/29
     */
    public static boolean isTokenExpired(String token) {
        Date now = Calendar.getInstance().getTime();
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().before(now);
    }

    /**
     * @Description: 生成随机盐, 长度32位
     * @Param: []
     * @return: java.lang.String
     * @Date: 2021/4/29
     */
    public static String generateSalt() {
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(16).toHex();
        return hex;
    }

}