package com.myf.emicake.dto;

import com.myf.emicake.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @ClassName com.myf.emicake.dto PhoneDTO
 * @Description
 * @Author Afengis
 * @Date 2021/2/14 13:51
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO implements Serializable {

    private static final long serialVersionUID = -8526311189522229411L;

    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = Constants.PHONE_REG,message = "手机号码格式不符")
    private  String phone;
}
