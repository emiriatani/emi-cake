package com.myf.emicake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName com.myf.emicake.dto ProductDTO
 * @Description
 * @Author Afengis
 * @Date 2021/2/20 12:06
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO  implements Serializable {
    private static final long serialVersionUID = 1972625250160879215L;

    /**
    商品id
    */
    @NotEmpty(message = "商品id不能为空")
    private Integer id;

    /**
    商品标题
    */
    private String title;



    /**
     * 商品详情页展示图
     */
    private String imgDisplay;

    /**
     * 商品介绍视频
     */
    private  String video;

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
     * 商品甜度、脂度、芝度规格
     */
    private String tasteSpec;

    /**
     * 商品价格
     */
    private BigDecimal price;



}
