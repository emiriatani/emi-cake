package com.myf.emicake.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    /**
    * 商品主键spu-id
    */
    private Integer id;

    /**
    * 商品名称
    */
    private String name;

    /**
    * 标题
    */
    private String title;

    /**
    * 分类id
    */
    private Integer catagoryId;

    /**
    * 商品轮播图(可用于开展活动)
    */
    private String imgBanner;

    /**
    * 商品缩略图
    */
    private String imgThumbnail;

    /**
    * 商品详情页展示图
    */
    private String imgDisplay;

    /**
    * 商品详情页介绍图
    */
    private String imgDetail;

    /**
    * 商品描述
    */
    private String description;

    /**
    * 商品所有规格
    */
    private String specList;

    /**
    * 商品价格
    */
    private BigDecimal price;

    /**
    * 库存量
    */
    private Integer stock;

    /**
    * 商品状态：0无效，1有效
    */
    private Byte productStatus;

    /**
    * 销售状态：0下架，1上架
    */
    private Byte saleStatus;

    /**
    * 活动名称
    */
    private String activityName;

    /**
    * 活动状态: 0不在活动中，1在活动中
    */
    private Byte activityStatus;

    /**
    * 排序
    */
    private Integer sort;

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

    private static final long serialVersionUID = 1L;
}