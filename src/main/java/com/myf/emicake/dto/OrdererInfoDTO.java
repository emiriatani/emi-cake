package com.myf.emicake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName com.myf.emicake.dto OrdererInfoDTO
 * @Description
 * @Author Afengis
 * @Date 2021/3/28 13:56
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdererInfoDTO implements Serializable {
    private static final long serialVersionUID = -1683522244538251599L;

    //@NotNull(message = "收货地址id不能为空")
    private Integer id;

    @NotNull(message = "用户id不能为空")
    private Integer memberId;

    private String ordererName;

    @NotNull(message = "订货人手机不能为空")
    private Long ordererPhone;

    private Byte defaultAddress;



}
