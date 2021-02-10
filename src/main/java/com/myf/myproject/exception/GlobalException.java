package com.myf.myproject.exception;

import lombok.Data;

/**
 * @ClassName com.myf.myproject.exception GlobalException
 * @Description  全局异常类
 * @Author Afengis
 * @Date 2021/2/10 21:36
 * @Version V1.0
 **/
@Data
public class GlobalException extends RuntimeException {

    private int code;

    public GlobalException(int code, String message) {
        super(message);
        this.code = code;
    }
}
