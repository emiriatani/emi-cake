package com.myf.emicake.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop implements Serializable {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 店铺名称
    */
    private String shopName;

    /**
    * 店铺地址
    */
    private String address;

    /**
    * 占地面积
    */
    private Double floorSpace;

    /**
    * 人员规模
    */
    private Integer staffSize;

    /**
    * 是否有效,0无效,1有效
    */
    private Byte state;

    /**
    * 营业状态,0不营业,1营业
    */
    private Byte operatingState;

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
    private Date deleteTime;

    private static final long serialVersionUID = 1L;
}