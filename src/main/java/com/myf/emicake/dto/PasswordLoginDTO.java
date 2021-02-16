package com.myf.emicake.dto;

import com.myf.emicake.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @ClassName com.myf.emicake.dto PasswordLoginDTO
 * @Description
 * @Author Afengis
 * @Date 2021/2/15 22:03
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordLoginDTO implements Serializable {

    private static final long serialVersionUID = 8844437001565108907L;

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = Constants.PHONE_REG,message = "用户名格式不符")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = Constants.PASSWORD_REG,message = "密码最少为6位")
    private String passWord;

}
