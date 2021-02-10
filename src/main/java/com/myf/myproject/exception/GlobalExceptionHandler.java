package com.myf.myproject.exception;

import com.myf.myproject.dto.Result;
import com.myf.myproject.dto.ResultEnum;
import com.myf.myproject.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName com.myf.myproject.exception GlobalExceptionHandler
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
        } else {
            log.error("系统异常", e);
            return ResultUtils.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }

}
