package com.myf.emicake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName com.myf.emicake.dto MemberAddressDTO
 * @Description
 * @Author Afengis
 * @Date 2021/3/28 13:37
 * @Version V1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberAddressDTO  implements Serializable {
    private static final long serialVersionUID = -7644647901542839422L;

    @NotNull(message = "收货地址id不能为空")
    private Integer id;

    @NotNull(message = "用户id不能为空")
    private Integer memberId;

    @NotBlank(message = "收货人姓名不能为空")
    private String consigneeName;

    @NotNull(message = "收货人手机不能为空")
    private Long consigneePhone;

    @NotBlank(message = "收货人所在省份不能为空")
    private String consigneeProvinces;

    @NotBlank(message = "收货人所在城市不能为空")
    private String consigneeCity;

    @NotBlank(message = "收货人所在地区不能为空")
    private String consigneeRegion;

    @NotBlank(message = "收货人具体地址不能为空")
    private String consigneeAddress;

    private Byte defaultAddress;


}
