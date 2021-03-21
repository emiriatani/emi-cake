package com.myf.emicake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName com.myf.emicake.dto ProductSkuDTO
 * @Description
 * @Author Afengis
 * @Date 2021/2/21 12:11
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSkuDTO implements Serializable {

    private static final long serialVersionUID = 3012264245033914016L;

    /**
     * 当前规格商品id
     */
    @NotEmpty(message = "商品id不能为空")
    private String id;

    /**
     * 当前规格商品展示图
     */
    private String images;

    /**
     * 当前规格商品的参数
     */
    private String productSpecs;

    /*当前规格商品的价格*/
    private BigDecimal price;



}
