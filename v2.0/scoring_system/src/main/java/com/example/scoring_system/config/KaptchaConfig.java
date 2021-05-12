package com.example.scoring_system.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
//        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
//        Properties properties = new Properties();
////        properties.setProperty("kaptcha.background.impl","255,255,255");
//        // 图片边框
//        properties.setProperty("kaptcha.border", "no");
//        // 边框颜色
//        properties.setProperty("kaptcha.border.color", "105,179,90");
//        // 字体颜色
//        properties.setProperty("kaptcha.textproducer.font.color", "red");
//        // 图片宽
//        properties.setProperty("kaptcha.image.width", "110");
//        // 图片高
//        properties.setProperty("kaptcha.image.height", "40");
//        // 字体大小
//        properties.setProperty("kaptcha.textproducer.font.size", "20");
//        // session key
//        properties.setProperty("kaptcha.session.key", "code");
//        // 验证码长度
//        properties.setProperty("kaptcha.textproducer.char.length", "4");
//        // 字体
//        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
//
//        properties.setProperty("kaptcha.noise.color", "35,37,38");
//
//        Config config = new Config(properties);
//        defaultKaptcha.setConfig(config);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", "160");
        properties.setProperty("kaptcha.image.height", "60");
        properties.setProperty("kaptcha.textproducer.font.size", "28");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.spac", "35");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "Arial,Courier");
        properties.setProperty("kaptcha.noise.color", "white");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}