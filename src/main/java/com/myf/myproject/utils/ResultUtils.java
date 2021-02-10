package com.myf.myproject.utils;

import com.myf.myproject.dto.Result;

/**
 * @ClassName com.myf.myproject.utils ResultUtils
 * @Description  生成Result工具类
 * @Author Afengis
 * @Date 2021/2/10 21:38
 * @Version V1.0
 **/
public class ResultUtils {

    public static Result error(int code,String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static Result<Object> success(int code, String msg, Object obj) {
        Result<Object> result = new Result<>(code, msg, obj);
        return result;
    }



}
