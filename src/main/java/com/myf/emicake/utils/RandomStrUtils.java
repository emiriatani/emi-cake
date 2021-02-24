package com.myf.emicake.utils;

import com.myf.emicake.common.Constants;

import java.util.Random;

/**
 * @ClassName com.myf.emicake.utils RandomStrUtils
 * @Description 随机生成各种字符串的工具类
 * @Author Afengis
 * @Date 2021/2/7 19:29
 * @Version V1.0
 **/
public class RandomStrUtils {


    /**
     * 随机生成n位字符串
     *
     * @param n 长度
     * @return
     */
    public static String getSalt(int n) {
        char[] chars = Constants.VERIFY_SALT_STR.toCharArray();
        StringBuilder sb = new StringBuilder();
        if (n >= 8) {
            for (int i = 0; i < n; i++) {
                char aChar = chars[new Random().nextInt(chars.length)];
                sb.append(aChar);
            }
        }else {
            getSalt(8);
        }

        return sb.toString();
    }

    /**
     * 随机生成n位短信验证码
     *
     * @param n
     * @return
     */
    public static String getSmsCode(int n) {
        char[] chars = Constants.VERIFY_SMS_CODE_STR.toCharArray();
        StringBuilder sb = new StringBuilder();
        if (n >= 4) {
            for (int i = 0; i < n; i++) {
                char aChar = chars[new Random().nextInt(chars.length)];
                sb.append(aChar);
            }
        } else {
            getSmsCode(6);
        }
        return sb.toString();
    }
}
