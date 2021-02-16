package com.myf.emicake.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authc.AuthenticationException;

/**
 * @ClassName com.myf.emicake.exception MyAuthenticationException
 * @Description  认证异常
 * @Author Afengis
 * @Date 2021/2/15 15:48
 * @Version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class MyAuthenticationException extends AuthenticationException {

    private int code;

    public MyAuthenticationException(int code, String message) {
        super(message);
        this.code = code;
    }


}
