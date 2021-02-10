package com.myf.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @EnumName com.myf.myproject.dto ResultEnum
 * @Description  Result信息枚举类，主要定义了各种异常代码和提示信息
 * @Author Afengis
 * @Date 2021/2/10 21:25
 * @Version V1.0
 **/
@Getter
@AllArgsConstructor
public enum ResultEnum {
    /*通用异常信息*/
    UNKNOWN_ERROR(500,"服务端错误"),

    /*短信验证码*/
    SMS_SUCCESS(200,"短信验证码发送成功"),
    SMS_ERROR(1001,"短信验证码发送失败"),

    /*注册*/
    REGISTER_SUCCESS(200,"注册成功"),
    ALREADY_REGISTER(2001,"该手机号已经注册");

    private int code;
    private String msg;

}
