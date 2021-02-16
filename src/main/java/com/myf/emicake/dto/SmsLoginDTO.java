package com.myf.emicake.dto;

import com.myf.emicake.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @ClassName com.myf.emicake.dto SmsLoginDTO
 * @Description
 * @Author Afengis
 * @Date 2021/2/15 13:47
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class  SmsLoginDTO  implements Serializable {

    private static final long serialVersionUID = -4609194719837427556L;

    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = Constants.PHONE_REG,message = "手机号码格式不符")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = Constants.SMS_CODE_REG,message = "验证码格式不符")
    private String smsCode;
}
