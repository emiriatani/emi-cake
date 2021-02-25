package com.myf.emicake.dto;

import lombok.*;

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
@Getter
@ToString
@EqualsAndHashCode
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
    private int number;

    /*商品单项总价*/
    private BigDecimal totalPrice;


    public void setProductSkuId(Integer productSkuId) {
        this.productSkuId = productSkuId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTotalPrice() {
        this.totalPrice = getPrice().multiply(BigDecimal.valueOf(getNumber()));
    }


}
