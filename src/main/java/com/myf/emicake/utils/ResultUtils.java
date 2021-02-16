package com.myf.emicake.utils;

import com.myf.emicake.common.Result;

/**
 * @ClassName com.myf.emicake.utils ResultUtils
 * @Description  生成Result工具类
 * @Author Afengis
 * @Date 2021/2/10 21:38
 * @Version V1.0
 **/
public class ResultUtils {

    /**
     * 请求失败
     * @param code
     * @param msg
     * @return
     */
    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /**
     * 请求成功并返回数据
     * @param code
     * @param msg
     * @param obj
     * @return
     */
    public static Result<Object> success(int code, String msg, Object obj) {
        Result<Object> result = new Result<>(code, msg, obj);
        return result;
    }

    /**
     * 请求成功不返回数据
     * @param code
     * @param msg
     * @return
     */
    public static Result success(int code, String msg) {
        Result result = new Result<>(code, msg);
        return result;
    }



}
