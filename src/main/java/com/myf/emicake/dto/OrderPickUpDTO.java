package com.myf.emicake.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

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
    /*自提日期*/
    /*格式化处理前端传来的数据*/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    /*格式化后台传给前端的数据*/
    private LocalDate pickupDate;
    /*自提时间 开始*/
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm:ss")
    private LocalTime pickupTimeStart;
    /*自提时间 结束*/
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm:ss")
    private LocalTime pickupTimeEnd;

    /*自提时间完整显示*/
    private String pickupTime;



}
