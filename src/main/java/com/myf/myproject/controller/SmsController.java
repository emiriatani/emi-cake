package com.myf.myproject.controller;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.myf.myproject.service.SendSmsService;
import com.myf.myproject.utils.RandomStrUtils;
import com.myf.myproject.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @ClassName com.myf.myproject.controller SendSMSController
 * @Description
 * @Author Afengis
 * @Date 2021/2/7 13:44
 * @Version V1.0
 **/
@Slf4j
@RestController
@RequestMapping("/send")
public class SmsController {

    @Autowired
    private SendSmsService sendSmsService;

    @Autowired
    private RedisUtils redisUtils;

    private final String register_template_code = "SMS_211486259";
    private final String login_template_code = "SMS_211486259";

    @RequestMapping(value = "/register/{phone}",method = RequestMethod.GET)
    public String getSmsCode(@PathVariable("phone") String phone) {

        //获取redis中的手机号
        String code = redisUtils.get(phone);
        //判断当前的code的不为空
        if (!StringUtils.isEmpty(code)) {
            return "短信已发出，验证码未失效，请及时使用";
        }
        //否则就是没有验证码，生成验证码并且存储到redis中，设置他的过期时间
        //生成随机验证码
        String smsCode = RandomStrUtils.getSmsCode(6);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", smsCode);
        //发送短信
        boolean isSend = false;
        try {
            isSend = sendSmsService.send(phone, register_template_code, map);
        } catch (ClientException e) {
            log.error("!",e);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (isSend) {
            //如果短信发送成功就放入到redis中，设置3分钟之后过期
            redisUtils.set(phone, smsCode, 3);
            return "短信验证码发送成功";
        } else {
            return "短信验证码发送失败";
        }
    }
}

