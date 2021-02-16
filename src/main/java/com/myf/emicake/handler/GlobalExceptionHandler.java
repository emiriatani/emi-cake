package com.myf.emicake.handler;

import com.myf.emicake.common.Result;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.exception.GlobalException;
import com.myf.emicake.exception.MyAuthenticationException;
import com.myf.emicake.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName com.myf.emicake.exception GlobalExceptionHandler
 * @Description 全局异常处理器
 * @Author Afengis
 * @Date 2021/2/10 21:48
 * @Version V1.0
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    public Result handle(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return ResultUtils.error(globalException.getCode(), globalException.getMessage());
        } else if (e instanceof IncorrectCredentialsException) {
            log.info("认证异常类是:" + e.getClass().getName());
            log.error("认证异常信息", e.getMessage());
            return ResultUtils.error(StatusCode.PASSWORD_ERROR.getCode(), StatusCode.PASSWORD_ERROR.getMsg());
        } else if (e instanceof AuthenticationException) {
            MyAuthenticationException myAuthenticationException = (MyAuthenticationException) e;
            return ResultUtils.error(myAuthenticationException.getCode(), myAuthenticationException.getMessage());
        } else {
            log.error("系统异常", e);
            return ResultUtils.error(StatusCode.UNKNOWN_ERROR.getCode(), StatusCode.UNKNOWN_ERROR.getMsg());
        }
    }


}
