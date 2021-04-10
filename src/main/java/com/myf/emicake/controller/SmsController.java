package com.myf.emicake.controller;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.myf.emicake.common.Constants;
import com.myf.emicake.common.Result;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.dto.PhoneDTO;
import com.myf.emicake.exception.GlobalException;
import com.myf.emicake.service.SmsService;
import com.myf.emicake.utils.RedisUtils;
import com.myf.emicake.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * @ClassName com.myf.emicake.controller SendSMSController
 * @Description
 * @Author Afengis
 * @Date 2021/2/7 13:44
 * @Version V1.0
 **/
@Slf4j
@RestController
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisUtils redisUtils;



    /**
     * 获取短信验证码
     * @param phoneDTO 短信DTO
     * @return
     * @throws ClientException
     * @throws JsonProcessingException
     */
    @PostMapping("/sms")
    //@RequestMapping(value = "/sms",method = RequestMethod.POST)
    public Result getSmsCode(@RequestBody @Valid PhoneDTO phoneDTO) throws ClientException, JsonProcessingException {

        String phone = phoneDTO.getPhone();

        //获取redis中该手机号是否存在已经发送的验证码
        if (redisUtils.exists(phone)){
            throw new GlobalException(StatusCode.SMS_ALREADY_SEND.getCode(), StatusCode.SMS_ALREADY_SEND.getMsg());
        }
        //生成6位随机验证码
        Map<String, Object> map = smsService.generateRandomSmsCode(6);
        //发送短信
        boolean isSend = false;

        isSend = smsService.send(phone, Constants.COMMON_TEMPLATE_CODE, map);

        if (isSend) {
            //如果短信发送成功就放入到redis中，设置3分钟之后过期
            redisUtils.set(phone, map.get("code"), 3);
            return ResultUtils.success(StatusCode.SMS_SEND_SUCCESS.getCode(), StatusCode.SMS_SEND_SUCCESS.getMsg());
        } else {
            log.error("异常信息", new Exception("短信验证码发送失败,请重新获取"));
            throw new GlobalException(StatusCode.SMS_SEND_ERROR.getCode(), StatusCode.SMS_ALREADY_SEND.getMsg());
        }
    }

    @PostMapping("/sms/remove")
    public Result deleteSmsCode(@RequestParam("id") String id){

        boolean b = smsService.deleteSmsCode(id);
        if (b) {
            return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg());
        }
        return ResultUtils.error(StatusCode.UNKNOWN_ERROR.getCode(), StatusCode.UNKNOWN_ERROR.getMsg());

    }
}

