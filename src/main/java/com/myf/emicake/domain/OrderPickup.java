package com.myf.emicake.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPickup implements Serializable {
    private Integer id;

    /**
    * 订单id
    */
    private Integer orderId;

    /**
    * 用户id
    */
    private Integer memberId;

    /**
    * 门店id
    */
    private Integer storeId;

    /**
    * 取货人姓名
    */
    private String pickupName;

    /**
    * 取货人手机号
    */
    private Long pickupPhone;

    /**
    * 取货日期
    */
    private LocalDate pickupDate;

    /**
    * 取货时间(开始)
    */
    private LocalTime pickupTimeStart;

    /**
    * 取货时间(结束)
    */
    private Date pickupTimeEnd;

    /**
    * 取货时间完整显示
    */
    private String pickupTime;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;

    /**
    * 更新时间
    */
    private LocalDateTime updateTime;

    /**
    * 删除时间
    */
    private LocalDateTime deleteTime;

    /**
    * 是否有效,0无效,1有效
    */
    private Byte state;

    private static final long serialVersionUID = 1L;
}