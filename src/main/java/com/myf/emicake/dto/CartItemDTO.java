package com.myf.emicake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName com.myf.emicake.dto CartItemDTO
 * @Description
 * @Author Afengis
 * @Date 2021/2/25 12:41
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO implements Serializable {

    private static final long serialVersionUID = 3336010185345542911L;

    /*商品SKU id*/
    private Integer productSkuId;

    /*商品标题*/
    private String title;

    /*商品规格*/
    private String spec;

    /*加入购物车时的价格*/
    private BigDecimal price;

    /*商品缩略图*/
    private String thumbnail;

    /*商品数量*/
    private int number;

    /*商品单项总价*/
    private BigDecimal totalPrice;


    public void setTotalPrice() {
        this.totalPrice = getPrice().multiply(BigDecimal.valueOf(getNumber()));
    }


}
