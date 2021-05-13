package com.example.scoring_system.utils;

import java.util.Random;

public class SaltUtils {

    /**
    * @Description:  生成盐的静态方法
    * @Param: [n]
    * @return: java.lang.String
    * @Date: 2021/4/14
    */
    public static String getSalt(int n)
    {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890!@#$%^&*()".toCharArray();
        StringBuilder sb=new StringBuilder();
        char tmp=chars[new Random().nextInt(chars.length)];
        sb.append(tmp);
        return sb.toString();
    }
}
