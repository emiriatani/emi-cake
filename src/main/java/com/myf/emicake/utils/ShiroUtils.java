package com.myf.emicake.utils;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;

/**
 * @ClassName com.myf.emicake.utils ShiroUtils
 * @Description shiro工具类
 * @Author Afengis
 * @Date 2021/2/15 10:33
 * @Version V1.0
 **/
public class ShiroUtils {

    /**
     * 指定位数生成盐值
     *
     * @param n 需要生成的盐值位数
     * @return
     */
    public static String generateSalt(int n) {
        if (n > 0) {
            return RandomStrUtils.getSalt(n);
        }
        return RandomStrUtils.getSalt(8);
    }

    /**
     * 获取加密后的密码，使用默认hash迭代的次数 1 次
     *
     * @param hashAlgorithm hash算法名称 MD2、MD5、SHA-1、SHA-256、SHA-384、SHA-512、etc。
     * @param password      需要加密的密码
     * @param salt          盐
     * @return 加密后的密码
     */
    public static String encryptPassword(String hashAlgorithm, String password, String salt) {
        return encryptPassword(hashAlgorithm, password, salt, 1);
    }

    /**
     * 获取加密后的密码，需要指定 hash迭代的次数
     *
     * @param hashAlgorithm  hash算法名称 MD2、MD5、SHA-1、SHA-256、SHA-384、SHA-512、etc。
     * @param password       需要加密的密码
     * @param salt           盐
     * @param hashIterations hash迭代的次数
     * @return 加密后的密码
     */
    public static String encryptPassword(String hashAlgorithm, String password, String salt, int hashIterations) {
        SimpleHash hash = new SimpleHash(hashAlgorithm, password, salt, hashIterations);
        return hash.toBase64();
    }

    public static boolean isAuthenticatedLoginUser(Subject subject,AuthenticationToken token) {
        boolean loginFlag = false;
        subject.login(token);
        if (subject.isAuthenticated()) {
            loginFlag = true;
            return loginFlag;
        }
        return loginFlag;
    }
}
