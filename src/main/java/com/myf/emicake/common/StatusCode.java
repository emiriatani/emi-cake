package com.myf.emicake.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @EnumName myf.myf.emicake.dto StatusCode
 * @Description  Result信息枚举类，主要定义了各种异常代码和提示信息
 * @Author Afengis
 * @Date 2021/2/10 21:25
 * @Version V1.0
 **/
@Getter
@AllArgsConstructor
public enum StatusCode implements Serializable {

    /*通用异常信息*/
    UNKNOWN_ERROR(Constants.SERVER_ERROR,"服务端错误"),


    /*短信验证码*/
    SMS_SEND_SUCCESS(Constants.SUCCESS,"短信验证码发送成功"),
    SMS_SEND_ERROR(Constants.FAIL,"短信验证码发送失败"),
    SMS_ALREADY_SEND(Constants.FAIL,"短信已发出，验证码未失效，请及时使用"),
    EMPTY_PHONE(Constants.FAIL,"手机号码不能为空"),
    SMS_CODE_ERROR(Constants.FAIL,"验证码错误，请重新输入"),
    SMS_CODE_INVALID(Constants.FAIL,"该验证码已失效，请重新获取"),


    /*注册*/
    REGISTER_SUCCESS(Constants.SUCCESS,"注册成功"),
    ALREADY_REGISTER(Constants.FAIL,"该手机号已经注册"),
    ID_NOT_EXISTS(Constants.FAIL,"该ID对应的会员信息不存在，请核实"),

    /*登录*/
    LOGIN_SUCCESS(Constants.SUCCESS,"登录成功"),
    LOGIN_FAILED(Constants.FAIL,"登录失败，请重新尝试"),
    NOT_REGISTER(Constants.FAIL,"该手机号码未注册，请先注册"),
    PASSWORD_ERROR(Constants.FAIL,"密码错误，请重新输入"),
    ACCOUNT_LOCKED(Constants.FAIL,"该账号已被锁定，请联系管理员");


    private Integer code;
    private String msg;

}
