package com.myf.emicake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName com.myf.emicake.dto PasswordDTO
 * @Description
 * @Author Afengis
 * @Date 2021/2/14 14:07
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO implements Serializable {

    private static final long serialVersionUID = -2125110855548445850L;
    @NotBlank(message = "id不能为空")
    private Integer id;

    @NotBlank(message = "旧密码不能为空")
    @Length(min = 6,message = "密码长度最短为6" )
    private String oldPassword;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6,message = "密码长度最短为6")
    private String newPassword;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6,message = "密码长度最短为6")
    private String confirmPassword;


}
