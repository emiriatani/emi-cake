package com.myf.emicake.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "商品id不能为空")
    /*商品Id*/
    private Integer productId;

    @NotEmpty(message = "商品sku-id不能为空")
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
    @Max(value = 100,message = "一次性加购最多为100件")
    private int number;

    /*商品单项总价*/
    private BigDecimal totalPrice;


    public void setTotalPrice(BigDecimal unitPrice,int number) {
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(number));
    }


}
