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
public class OrderDetail implements Serializable {
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
    * 商品id
    */
    private Integer productId;

    /**
    * 商品名称
    */
    private String productName;

    /**
    * 商品sku
    */
    private Integer productSku;

    /**
    * 商品图片
    */
    private String productImage;

    /**
    * 商品单价
    */
    private BigDecimal productPrice;

    /**
    * 购买数量
    */
    private Integer purchaseQuantity;

    /**
    * 小计金额
    */
    private BigDecimal subtotalAmount;

    /**
    * 是否有效,0无效,1有效
    */
    private Byte state;

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