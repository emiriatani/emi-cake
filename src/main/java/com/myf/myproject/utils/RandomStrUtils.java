package com.myf.myproject.utils;

import java.util.Random;

/**
 * @ClassName com.myf.myproject.utils RandomStrUtils
 * @Description 随机生成各种字符串的工具类
 * @Author Afengis
 * @Date 2021/2/7 19:29
 * @Version V1.0
 **/
public class RandomStrUtils {

    public static final String VERIFY_SALT_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890!@#$%^&*()";
    public static final String VERIFY_SMS_CODE_STR = "0123456789";


    /**
     * 随机生成n位盐值
     * @param n 长度
     * @return
     */
    public static String getSalt(int n){
        char[] chars = VERIFY_SALT_STR.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }

    /**
     * 随机生成n位短信验证码
     * @param n
     * @return
     */
    public static String getSmsCode(int n){
        char[] chars = VERIFY_SMS_CODE_STR.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }
}
