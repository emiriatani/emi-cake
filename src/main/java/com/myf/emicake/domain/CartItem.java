package com.myf.emicake.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName com.myf.emicake.domain CartItem
 * @Description
 * @Author Afengis
 * @Date 2021/2/23 21:30
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {


    private static final long serialVersionUID = 3308643599501684456L;

    /*商品id*/
    private Integer id;

    /*sku id*/
    private Integer skuId;

    /*商品标题*/
    private String title;

    /*商品缩略图*/
    private String thumbnail;

    /*商品规格,json字符串*/
    private String spec;

    /*商品单价*/
    private BigDecimal price;

    /*库存状态，是否有货*/
    private boolean stockState;

    /*购买数量*/
    private int count;

    /*单项商品总价*/
    private BigDecimal totalPrice;

    public void setTotalPrice(BigDecimal totalPrice) {
        totalPrice = price.multiply(BigDecimal.valueOf(count));
        this.totalPrice = totalPrice;
    }
}
