package com.myf.emicake.utils;

import com.myf.emicake.domain.Member;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @ClassName com.myf.emicake.utils MyBeanUtils
 * @Description
 * @Author Afengis
 * @Date 2021/2/14 13:12
 * @Version V1.0
 **/
public class MyBeanUtils {


    /**
     * 自定义封装
     * @param phone 注册的手机号
     * @param request 发送的请求
     * @return
     */
    public static Member param2bean(String phone,HttpServletRequest request){
                Member member = new Member();
                member.setMemberId(phone);
                member.setPhone(phone);
                member.setCreateIp(NetUtils.ipToInt(NetUtils.getClientIpAddress(request)));
                member.setCreateTime(LocalDateTime.now());
                return member;
    }


    /**
     *
     * @param id  会员id
     * @param password 设置的密码(已加密)
     * @param salt 盐值
     * @return
     */
    public static Member param2bean(Integer id ,String password,String salt){
        Member member = new Member();
        member.setId(id);
        member.setMemberPassword(password);
        member.setSalt(salt);
        return member;
    }


}
