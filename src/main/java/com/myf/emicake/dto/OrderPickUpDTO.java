package com.myf.emicake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName com.myf.emicake.dto OrderPickUpDTO
 * @Description
 * @Author Afengis
 * @Date 2021/4/6 21:03
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPickUpDTO implements Serializable {
    private static final long serialVersionUID = 1970410522845131677L;


    private Integer id;

    /*订单id*/
    private Integer orderId;
    /*会员id*/
    private Integer memberId;
    /*门店id*/
    private Integer storeId;
    /*自提人姓名*/
    private String pickupName;
    /*自提人手机*/
    private Long pickupPhone;
    /*自提时间 开始*/
    private LocalDateTime pickupTimeStart;
    /*自提时间 结束*/
    private LocalDateTime pickupTimeEnd;



}
