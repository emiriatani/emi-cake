package com.myf.emicake.common;

import lombok.*;

import java.io.Serializable;

/**
 * @ClassName myf.myf.emicake.domain Result
 * @Description 通用请求结果返回类
 * @Author Afengis
 * @Date 2021/2/10 21:00
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -450697549442764520L;

    @NonNull
    private Integer code;
    @NonNull
    private String msg;
    private T data;

}
