package com.myf.emicake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName com.myf.emicake.dto OrderDetailDTO
 * @Description
 * @Author Afengis
 * @Date 2021/4/5 21:29
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO implements Serializable {
    private static final long serialVersionUID = -2667781337981992346L;

    private Integer id;
    /*订单id*/
    private Integer orderId;
    /*会员id*/
    private Integer memberId;
    /*商品id*/
    private Integer productId;
    /*商品名称*/
    private String  productName;
    /*商品Sku id*/
    private Integer productSku;
    /*商品缩略图*/
    private String productImage;
    /*商品单价*/
    private BigDecimal productPrice;
    /*购买数量*/
    private Integer purchaseQuantity;
    /*小计金额*/
    private BigDecimal subtotalAmount;


}
