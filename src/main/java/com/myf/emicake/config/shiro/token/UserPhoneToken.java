package com.myf.emicake.config.shiro.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 * @ClassName com.myf.emicake.config UserPhoneToken
 * @Description 自定义token
 * @Author Afengis
 * @Date 2021/2/15 15:23
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneToken extends UsernamePasswordToken implements Serializable {

    private static final long serialVersionUID = -5154191573524308433L;

    private String phone;
    private String smsCode;

    @Override
    public Object getPrincipal() {
        if (StringUtils.isNotBlank(phone)){
            return getPhone();
        }else{
            return "";
        }
    }

    @Override
    public Object getCredentials() {
        if (StringUtils.isNotBlank(phone)){
            return getSmsCode();
        }else{
            return "";
        }
    }



}
