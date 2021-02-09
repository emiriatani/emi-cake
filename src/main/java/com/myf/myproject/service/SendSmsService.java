package com.myf.myproject.service;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

/**
 * @InterfaceName com.myf.myproject.service SendSmsService
 * @Description
 * @Author Afengis
 * @Date 2021/2/7 15:15
 * @Version V1.0
 **/
public interface SendSmsService {

    boolean send(String phoneNum, String templateCode, Map<String, Object> code) throws ClientException, JsonProcessingException;


}
