package com.myf.emicake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName com.myf.emicake.dto OrderAddressDTO
 * @Description
 * @Author Afengis
 * @Date 2021/4/6 21:07
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddressDTO implements Serializable {
    private static final long serialVersionUID = -8793088654155876842L;

    private Integer id;

    private Integer orderId;

    private Integer addressId;

    private LocalDateTime deliveryTime;


}
