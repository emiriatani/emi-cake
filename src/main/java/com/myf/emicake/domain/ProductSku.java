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
public class ProductSku implements Serializable {
    /**
    * 商品规格sku-id
    */
    private Integer id;

    /**
    * 当前规格的商品id，与product表中的id对应
    */
    private Integer productId;

    /**
    * 商品标题
    */
    private String title;

    /**
    * 当前规格的商品宣传图，中间用&分隔
    */
    private String images;

    /**
    * 当前商品的规格参数
    */
    private String productSpecs;

    /**
    * 当前规格的商品宣传文案
    */
    private String specificationDesc;

    /**
    * 当前规格的商品价格
    */
    private BigDecimal price;

    /**
    * 商品状态，0无效，1有效
    */
    private Byte productStatus;

    /**
    * 销售状态，0下架，1上架
    */
    private Byte saleStatus;

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