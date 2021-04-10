package com.myf.emicake.service;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

/**
 * @InterfaceName myf.myf.emicake.service SmsService
 * @Description
 * @Author Afengis
 * @Date 2021/2/7 15:15
 * @Version V1.0
 **/
public interface SmsService {

    boolean send(String phoneNum, String templateCode, Map<String, Object> code) throws ClientException, JsonProcessingException;

    boolean deleteSmsCode(String memberId);

    Map<String, Object> generateRandomSmsCode(int number);
}
