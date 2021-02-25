package com.myf.emicake.common;

/**
 * @ClassName myf.myf.emicake.utils Constants
 * @Description 常量工具类
 * @Author Afengis
 * @Date 2021/2/11 10:42
 * @Version V1.0
 **/
public class Constants {


    /*-------------------短信模板代码---------------*/
    /*验证码短信模板code*/
    public static final String COMMON_TEMPLATE_CODE = "SMS_211492984";
    public static final  String REGISTER_TEMPLATE_CODE = "SMS_211486259";


    /*验证*/
    public static final String VERIFY_SALT_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890!@#$%^&*()";
    public static final String VERIFY_SMS_CODE_STR = "0123456789";
    public static final String PHONE_REG = "^1[3|4|5|7|8][0-9]{9}$";
    public static final String SMS_CODE_REG = "^\\d{6}$";
    public static final String PASSWORD_REG = "\\S{6,}";
    public static final String LOGIN_MEMBER_KEY = "loginMember";
    public static final String BANNER_LIST_KEY = "bannerList";
    public static final String CART_KEY_PREFIX = "cart:";

    /*日期格式化样式*/
    public static final String FORMAT_DATE_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /*整型常量*/
    public static final int HASH_ITERATIONS = 1024;
    public static final int NO_LOGIN_MEMBER_FLAG = -1;


    /*----------------请求响应状态码---------------*/
    /*服务器异常*/
    public static final Integer SERVER_ERROR = 500;
    /*请求成功*/
    public static final Integer SUCCESS = 200;
    /*请求失败*/
    public static final Integer FAIL = 400;

}
