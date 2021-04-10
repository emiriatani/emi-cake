package com.myf.emicake.service.Impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.myf.emicake.component.properties.AliyunSmsProperties;
import com.myf.emicake.domain.Member;
import com.myf.emicake.service.MemberService;
import com.myf.emicake.service.SmsService;
import com.myf.emicake.utils.JSONUtils;
import com.myf.emicake.utils.RandomStrUtils;
import com.myf.emicake.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
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
public class SmsServiceImpl implements SmsService {

    @Autowired
    private JSONUtils jsonUtils;

    @Autowired
    private AliyunSmsProperties aliyunSmsProperties;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MemberService memberService;



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
    public boolean send(String phoneNum, String templateCode, Map<String, Object> code) throws ClientException {

        System.out.println(aliyunSmsProperties);
        //设置超时时间-可自行调整
        System.setProperty(aliyunSmsProperties.getConnectTimeout(), "3000");
        System.setProperty(aliyunSmsProperties.getReadTimeout(), "2000");
        //初始化acsClient需要的几个参数
        String product = aliyunSmsProperties.getProduct();//短信API产品名称（短信产品名固定，无需修改）
        String domain = aliyunSmsProperties.getDomain();//短信API产品域名（接口地址固定，无需修改）
        String regionId = aliyunSmsProperties.getRegionId();
        String sysVersion = aliyunSmsProperties.getSysVersion();
        String sysAction = aliyunSmsProperties.getSysAction();
        String accessKeyId = aliyunSmsProperties.getAccessKeyId();//你的accessKeyId
        String accessKeySecret = aliyunSmsProperties.getAccessKeySecret();//你的accessKeySecret
        String signName = aliyunSmsProperties.getSignName();
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

    /**
     * 删除redis中的已失效的短信验证码
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteSmsCode(String id) {

        Member member = memberService.selectByPrimaryKey(Integer.valueOf(id));
        if (!ObjectUtils.isEmpty(member)){
            if (redisUtils.exists(member.getMemberId())){
                long result = redisUtils.remove(member.getMemberId());
                if (result > 0 ){
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public Map<String, Object> generateRandomSmsCode(int number) {
        String smsCode = RandomStrUtils.getSmsCode(number);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", smsCode);
        return map;
    }


}