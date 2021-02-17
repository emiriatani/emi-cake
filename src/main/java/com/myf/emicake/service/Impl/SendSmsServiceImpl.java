package com.myf.emicake.service.Impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.myf.emicake.service.SendSmsService;
import com.myf.emicake.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName com.myf.emicake.service.Impl SendSmsServiceImpl
 * @Description 发送短信验证码实现类
 * @Author Afengis
 * @Date 2021/2/7 15:15
 * @Version V1.0
 **/
@Service
@Slf4j
public class SendSmsServiceImpl implements SendSmsService {

    @Autowired
    private JSONUtils jsonUtils;


    /***
     * @ClassName com.myf.emicake.service.Impl SendSmsServiceImpl
     * @MethodName send
     * @Description 发送短信
     * @Param phoneNum: 发送的手机号 templateCode: 短信模板 code: 短信验证码
     * @return: boolean 是否发送成功
     * @Exception
     * @Author Afengis
     * @Date 2021/2/7 18:45
     **/
    @Override
    public boolean send(String phoneNum, String templateCode, Map<String, Object> code) throws JsonProcessingException, ClientException {

        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "3000");
        System.setProperty("sun.net.client.defaultReadTimeout", "2000");
        //初始化acsClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        final String regionId = "cn-hangzhou";
        final String sysVersion = "2017-05-25";
        final String sysAction = "SendSms";
        final String accessKeyId = "LTAI4GD4zszMZV2dBT87cUJm";//你的accessKeyId
        final String accessKeySecret = "QKs6wjrsJkbpTYOpekzyrudaBC3bYm";//你的accessKeySecret
        final String signName = "打工人的技术沉淀屋";
        //初始化DefaultAcsClient,暂时不支持多region（请勿修改）
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        IAcsClient DefaultAcsClient = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(domain);
        request.setSysVersion(sysVersion);
        request.setSysAction(sysAction);
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", jsonUtils.Object2JSON(code));
        CommonResponse response = DefaultAcsClient.getCommonResponse(request);
        log.info("发送短信响应数据为:" + response.getData());
        //发送验证码是否成功,成功就返回
        return response.getHttpResponse().isSuccess();
    }
}