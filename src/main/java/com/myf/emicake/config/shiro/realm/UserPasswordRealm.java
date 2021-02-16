package com.myf.emicake.config.shiro.realm;

import com.myf.emicake.common.StatusCode;
import com.myf.emicake.domain.Member;
import com.myf.emicake.exception.MyAuthenticationException;
import com.myf.emicake.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName com.myf.emicake.config UserPasswordRealm
 * @Description 用户名密码登录Realm
 * @Author Afengis
 * @Date 2021/2/15 19:41
 * @Version V1.0
 **/
@Slf4j
public class UserPasswordRealm extends AuthorizingRealm {

    @Autowired
    private MemberService memberService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = null;

        if(authenticationToken instanceof UsernamePasswordToken){
            token = (UsernamePasswordToken) authenticationToken;
        }else{
            return null;
        }

        String username = token.getUsername();

        if (StringUtils.isBlank(username)){
            return null;
        }

        if (!memberService.isExistsMember(username)) {
            /*用户名未注册*/
            throw new MyAuthenticationException(StatusCode.NOT_REGISTER.getCode(), StatusCode.NOT_REGISTER.getMsg());
        } else {
            Member member = memberService.selectByMemberId(username);

            if (member.getMemberStatus() != 0){
                // 主体，一般存用户名或用户实例对象，用于在其他地方获取当前认证用户信息
                // 凭证，这里是从数据库取出的加密后的密码，Shiro会用于与token中的密码比对
                String hashedCredentials = member.getMemberPassword();
                log.info("数据库加密的密码为:" + hashedCredentials);
                // 盐值
                ByteSource credentialsSalt = ByteSource.Util.bytes(member.getSalt());

                return new SimpleAuthenticationInfo(member,hashedCredentials,credentialsSalt,this.getName());
            }else {
                /*账号锁定*/
                throw new MyAuthenticationException(StatusCode.ACCOUNT_LOCKED.getCode(), StatusCode.ACCOUNT_LOCKED.getMsg());
            }
        }
    }

    @Override
    public boolean supports(AuthenticationToken obj){
        return obj instanceof UsernamePasswordToken;
    }

}
