package com.myf.emicake.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapLikeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName com.myf.emicake.utils JSONUtils
 * @Description JSON工具类
 * @Author Afengis
 * @Date 2021/2/7 19:19
 * @Version V1.0
 **/
@Slf4j
@Component
public class JSONUtils {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 将Java对象转为JSON字符串
     * <p>
     *
     * @param obj
     * @param <T>
     * @return
     */
    public <T> String Object2JSON(T obj) {
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Java对象转JSON字符串异常", e);
        }
        return jsonStr;
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * <p>
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public <T> T JSON2Object(String json, Class<T> type) {
        T obj = null;
        try {
            obj = objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            log.error("JSON字符串转Java对象异常", e);
        }
        return obj;
    }

    /**
     * 将JSON转成相应的List
     *
     * @param jsonStr
     * @return
     */
    public List JSON2List(String jsonStr) {
        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Object.class);
        List list = null;
        try {
            list = objectMapper.readValue(jsonStr, javaType);
        } catch (JsonProcessingException e) {
            log.error("JSON字符串转List异常", e);
        }
        return list;
    }

    /**
     * 将JSON转成相应的Map
     * @param jsonStr
     * @return
     */
    public Map<String, Object> JSON2Map(String jsonStr) {
        MapLikeType mapLikeType = objectMapper.getTypeFactory().constructMapLikeType(HashMap.class, String.class, Object.class);
        Map map = null;
        try {
            map = objectMapper.readValue(jsonStr, mapLikeType);
        } catch (JsonProcessingException e) {
            log.error("JSON字符串转Map异常", e);
        }
        return map;
    }


    /**
     *
     * @param fromValue 需要转换的对象
     * @param toValueTypeRef 需要转换成的类型引用
     * @param <T>
     * @return
     */
    public  <T> T myValueTypeConvert(Object fromValue, TypeReference<T> toValueTypeRef){

        T t = objectMapper.convertValue(fromValue, toValueTypeRef);
        return t;
    }

}
