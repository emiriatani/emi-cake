package com.myf.emicake.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddress implements Serializable {

    private Integer id;

    /**
    * 用户id
    */
    private Integer memberId;

    /**
    * 订货人姓名
    */
    private String ordererName;

    /**
    * 订货人手机号
    */
    private Long ordererPhone;

    /**
    * 收货人姓名
    */
    private String consigneeName;

    /**
    * 收货人手机号
    */
    private Long consigneePhone;

    /**
    * 省份
    */
    private String consigneeProvinces;

    /**
    * 城市
    */
    private String consigneeCity;

    /**
    * 地区
    */
    private String consigneeRegion;

    /**
    * 详细地址
    */
    private String consigneeAddress;

    /**
    * 邮编
    */
    private Integer consigneeZip;

    /**
    * 是否是默认地址，0否1是
    */
    private Byte defaultAddress;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;

    /**
    * 更新时间
    */
    private LocalDateTime updateTime;

    /**
    * 是否有效，0无效，1有效
    */
    private Byte state;

    /**
    * 删除时间
    */
    private Date deleteTime;

    private static final long serialVersionUID = 1L;
}