package com.myf.emicake.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapLikeType;
import lombok.extern.slf4j.Slf4j;

import com.myf.emicake.common.Constants;
import java.text.SimpleDateFormat;
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
public class JSONUtils {


    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 设置null时候不序列化(只针对对象属性)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 单引号处理
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        //序列化时候统一日期格式
        objectMapper.setDateFormat(new SimpleDateFormat(Constants.FORMAT_DATE_DEFAULT));
    }

    /**
     * 将Java对象转为JSON字符串
     * <p>
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String Object2JSON(T obj) throws JsonProcessingException {
        String jsonStr;
        jsonStr = objectMapper.writeValueAsString(obj);
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
    public static <T> T JSON2Object(String json, Class<T> type) throws JsonProcessingException {
        T obj;
        obj = objectMapper.readValue(json, type);

        return obj;
    }

    /**
     * 将JSON转成相应的List
     *
     * @param jsonStr
     * @return
     */
    public static List JSON2List(String jsonStr) throws JsonProcessingException {
        JavaType javaType = JSONUtils.objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Object.class);
        return objectMapper.readValue(jsonStr, javaType);
    }

    /**
     * 将JSON转成相应的Map
     *
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> JSON2Map(String jsonStr) throws JsonProcessingException {
        MapLikeType mapLikeType = JSONUtils.objectMapper.getTypeFactory().constructMapLikeType(HashMap.class, String.class, Object.class);
        return objectMapper.readValue(jsonStr, mapLikeType);
    }



}
