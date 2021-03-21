package com.myf.emicake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName com.myf.emicake.dto ShopDTO
 * @Description
 * @Author Afengis
 * @Date 2021/3/21 16:56
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO implements Serializable {

    private static final long serialVersionUID = -9027235654171691887L;

    private Integer id ;

    private String shopName;

    private String address;

    private Byte state;

    private Byte operatingState;


}
