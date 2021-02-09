package com.myf.myproject.controller;

import com.myf.myproject.entity.Member;
import com.myf.myproject.service.MemberService;
import com.myf.myproject.utils.NetUtils;
import com.myf.myproject.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @ClassName com.myf.myproject.controller MemberController
 * @Description
 * @Author Afengis
 * @Date 2021/2/5 20:15
 * @Version V1.0
 **/
@Slf4j
@Controller
public class MemberController {

        @Autowired
        private RedisUtils redisUtils;

        @Autowired
        private MemberService memberService;


        @PostMapping("/member/register")
        public String registerFirstStep(Model model, Member member, @RequestParam("SmsCode") String reqSmsCode, HttpServletRequest request){


            if (redisUtils.exists(member.getPhone())){
                //存在该手机号发送的验证码时
                String phoneSmsCode  = redisUtils.get(member.getPhone());
                if (reqSmsCode.equals(phoneSmsCode)){
                    log.info("注册验证码:" + reqSmsCode);
                    /*设置会员用户名*/
                    member.setMemberId(member.getPhone());
                    /*设置用户状态*/
                    member.setStatus(true);
                    /*设置注册时的ip地址*/
                    member.setCreateIp(NetUtils.ipToInt(NetUtils.getClientIpAddress(request)));
                    log.info("注册ip：" + NetUtils.intToIp(member.getCreateIp()));
                    /*设置注册时间*/
                    member.setCreateTime(LocalDateTime.now());
                    log.info("注册时间:"+member.getCreateTime());
                    /*设置更新时间*/
                    member.setUpdateTime(LocalDateTime.now());

                    log.info("保存的用户信息:" + member);
                    model.addAttribute("member",member);
                    return "member/setPassword";
                }else {
                    log.info("验证码错误");
                }
            }else {
                log.error("msg", new RuntimeException("验证码失效"));
            }
            return "member/register";
        }



}
