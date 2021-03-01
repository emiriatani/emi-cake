package com.myf.emicake.controller;

import com.myf.emicake.common.Constants;
import com.myf.emicake.common.Result;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.config.shiro.token.UserPhoneToken;
import com.myf.emicake.domain.Member;
import com.myf.emicake.dto.MemberDTO;
import com.myf.emicake.dto.PasswordLoginDTO;
import com.myf.emicake.dto.RegisterDTO;
import com.myf.emicake.dto.SmsLoginDTO;
import com.myf.emicake.exception.GlobalException;
import com.myf.emicake.service.MemberService;
import com.myf.emicake.utils.MyBeanUtils;
import com.myf.emicake.utils.RedisUtils;
import com.myf.emicake.utils.ResultUtils;
import com.myf.emicake.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName com.myf.emicake.controller MemberController
 * @Description
 * @Author Afengis
 * @Date 2021/2/5 20:15
 * @Version V1.0
 **/
@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MemberService memberService;


    /**
     * 短信验证码注册
     * @param registerDTO 注册DTO
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/register")
    public Result<Object> register(@RequestBody @Valid RegisterDTO registerDTO,
                                   HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        String phone = registerDTO.getPhone();
        log.info("注册的手机号码:" + phone);
        if (memberService.isExistsMember(phone)) {
            //该手机号已经注册
            throw new GlobalException(StatusCode.ALREADY_REGISTER.getCode(), StatusCode.ALREADY_REGISTER.getMsg());
        } else {
            if (redisUtils.exists(phone)) {
                //存在该手机号发送的验证码
                String phoneSmsCode = redisUtils.get(phone);
                String reqSmsCode = registerDTO.getSmsCode();
                log.info("注册的短信验证码:" + reqSmsCode);
                //if ("123456".equals(reqSmsCode)) {
                if (phoneSmsCode.equals(reqSmsCode)) {
                    /*封装用户信息*/
                    Member member = MyBeanUtils.param2bean(phone, request);
                    /*保存用户*/
                    memberService.insertSelective(member);
                    log.info("该会员id为:" + member.getId().toString());
                    /*查询出该用户的完整信息*/
                    member = memberService.selectByPrimaryKey(member.getId());
                    MemberDTO memberDTO = new MemberDTO();
                    BeanUtils.copyProperties(memberDTO, member);
                    return ResultUtils.success(StatusCode.REGISTER_SUCCESS.getCode(), StatusCode.REGISTER_SUCCESS.getMsg(), memberDTO);
                } else {
                    log.error("异常信息", new Exception("验证码错误"));
                    throw new GlobalException(StatusCode.SMS_CODE_ERROR.getCode(), StatusCode.SMS_CODE_ERROR.getMsg());
                }
            } else {
                log.error("异常信息", new Exception("该验证码已失效"));
                throw new GlobalException(StatusCode.SMS_CODE_INVALID.getCode(), StatusCode.SMS_CODE_INVALID.getMsg());
            }
        }
    }


    /**
     * 设置密码
     * @param id 会员主键id
     * @param member
     * @param model
     * @return
     */
    @PostMapping("/setPassword")
    public String setMemberPassword(@RequestParam("id") Integer id,
                                    Member member,
                                    Model model) {

        Member selectMember = memberService.selectByPrimaryKey(id);

        if (ObjectUtils.isEmpty(selectMember)) {
            throw new GlobalException(StatusCode.ID_NOT_EXISTS.getCode(), StatusCode.ID_NOT_EXISTS.getMsg());
        } else {
            log.info("会员id:" + id.toString());
            String salt = ShiroUtils.generateSalt(8);
            log.info("生成的盐值:" + salt);
            String memberPassword = member.getMemberPassword();
            log.info("用户设置的密码:" + member.getMemberPassword());
            String encryptedPassword = ShiroUtils.encryptPassword(Sha256Hash.ALGORITHM_NAME, memberPassword, salt, Constants.HASH_ITERATIONS);
            log.info("加密后的密码:" + encryptedPassword);
            member = MyBeanUtils.param2bean(id, encryptedPassword, salt);
            memberService.updateByPrimaryKeySelective(member);
        }
        return "redirect:/page/member/login.html";
    }


    /**
     * 短信验证码登录
     * @param smsLoginDTO 短信登录DTO
     * @param httpSession
     * @return
     */
    @ResponseBody
    @PostMapping("/smsLogin")
    public Result<Object> smsLogin(@RequestBody @Valid SmsLoginDTO smsLoginDTO, HttpSession httpSession,HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        String phone = smsLoginDTO.getPhone();
        String reqSmsCode = smsLoginDTO.getSmsCode();
        log.info("登录的手机号码：" + phone);
        log.info("登录的验证码：" + reqSmsCode);
        MemberDTO memberDTO = new MemberDTO();
        Subject subject = SecurityUtils.getSubject();
        UserPhoneToken userPhoneToken = new UserPhoneToken(phone, reqSmsCode);
        if (ShiroUtils.isAuthenticatedLoginUser(subject, userPhoneToken)) {
            Member member = (Member) subject.getPrincipal();
            memberService.updateByPrimaryKeySelective(MyBeanUtils.param2bean(member, request));
            BeanUtils.copyProperties(memberDTO, member);
            httpSession.setAttribute(Constants.LOGIN_MEMBER_KEY, memberDTO);
            subject.getSession().setAttribute(Constants.LOGIN_MEMBER_KEY, memberDTO);
            return ResultUtils.success(StatusCode.LOGIN_SUCCESS.getCode(), StatusCode.LOGIN_SUCCESS.getMsg(), memberDTO);
        }
        return ResultUtils.error(StatusCode.LOGIN_FAILED.getCode(), StatusCode.LOGIN_FAILED.getMsg());
    }

    /**
     * 用户名密码登录
     * @param passwordLoginDTO 密码登录DTO
     * @param httpSession
     * @return
     */
    @ResponseBody
    @PostMapping("/passwordLogin")
    public Result<Object> passwordLogin(@RequestBody @Valid PasswordLoginDTO passwordLoginDTO, HttpSession httpSession, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {

        String userName = passwordLoginDTO.getUserName();
        String passWord = passwordLoginDTO.getPassWord();

        log.info("登录的用户名：" + userName);
        log.info("登录的密码：" + passWord);
        MemberDTO memberDTO = new MemberDTO();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, passWord);
        if (ShiroUtils.isAuthenticatedLoginUser(subject,usernamePasswordToken)) {
            Member member = (Member) subject.getPrincipal();
            memberService.updateByPrimaryKeySelective(MyBeanUtils.param2bean(member, request));
            BeanUtils.copyProperties(memberDTO, member);
            httpSession.setAttribute(Constants.LOGIN_MEMBER_KEY, memberDTO);
            subject.getSession().setAttribute(Constants.LOGIN_MEMBER_KEY, memberDTO);
            return ResultUtils.success(StatusCode.LOGIN_SUCCESS.getCode(), StatusCode.LOGIN_SUCCESS.getMsg(), memberDTO);
        }
        return ResultUtils.error(StatusCode.LOGIN_FAILED.getCode(), StatusCode.LOGIN_FAILED.getMsg());
    }


}
