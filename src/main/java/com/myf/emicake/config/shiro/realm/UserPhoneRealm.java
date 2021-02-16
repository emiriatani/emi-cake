package com.myf.emicake.config.shiro.realm;

import com.myf.emicake.common.StatusCode;
import com.myf.emicake.config.shiro.token.UserPhoneToken;
import com.myf.emicake.domain.Member;
import com.myf.emicake.exception.MyAuthenticationException;
import com.myf.emicake.service.MemberService;
import com.myf.emicake.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName com.myf.emicake.config UserPhoneRealm
 * @Description 短信验证码登录Realm
 * @Author Afengis
 * @Date 2021/2/7 10:52
 * @Version V1.0
 **/
@Slf4j
public class UserPhoneRealm extends AuthorizingRealm {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UserPhoneToken userPhoneToken = null;
        Member member = null;

        if (authenticationToken instanceof AuthenticationToken) {
            userPhoneToken = (UserPhoneToken) authenticationToken;
        } else {
            return null;
        }
        String phone = (String) userPhoneToken.getPrincipal();
        if (StringUtils.isBlank(phone)) {
            return null;
        }

        if (!memberService.isExistsMember(phone)) {
            /*手机号未注册*/
            throw new MyAuthenticationException(StatusCode.NOT_REGISTER.getCode(), StatusCode.NOT_REGISTER.getMsg());
        } else {
            member = memberService.selectByMemberPhone(phone);
            if (member.getMemberStatus() != 0) {
                if (redisUtils.exists(phone)) {
                    /*获取请求传过来的验证码*/
                    String reqSmsCode = (String) userPhoneToken.getCredentials();
                    /*查询redis中的验证码*/
                    String phoneSmsCode = redisUtils.get(phone);
                    log.info("查询出的验证码:" + phoneSmsCode);
                    if (!phoneSmsCode.equals(reqSmsCode)) {
                        //if (!"123456".equals(reqSmsCode)) {
                        /*验证码错误*/
                        throw new MyAuthenticationException(StatusCode.SMS_CODE_ERROR.getCode(), StatusCode.SMS_CODE_ERROR.getMsg());
                    } else {
                        //return new SimpleAuthenticationInfo(member,Base64.encodeToString(phone.getBytes()),this.getName());
                        return new SimpleAuthenticationInfo(member, reqSmsCode, this.getName());

                    }
                } else {
                    /*验证码失效*/
                    throw new MyAuthenticationException(StatusCode.SMS_CODE_INVALID.getCode(), StatusCode.SMS_CODE_INVALID.getMsg());
                }
            } else {
                /*账号锁定*/
                throw new MyAuthenticationException(StatusCode.ACCOUNT_LOCKED.getCode(), StatusCode.ACCOUNT_LOCKED.getMsg());
            }
        }
    }


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UserPhoneToken;
    }

}
