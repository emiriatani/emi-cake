package com.myf.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName com.myf.myproject.entity Result
 * @Description 通用请求结果返回类
 * @Author Afengis
 * @Date 2021/2/10 21:00
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Result<T> implements Serializable {

    private static final long serialVersionUID = -450697549442764520L;

    private int code;
    private String msg;
    private T data;

}
