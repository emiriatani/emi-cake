package com.myf.emicake.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStock implements Serializable {
    /**
    * 商品库存主键
    */
    private Integer id;

    /**
    * 库存对应的商品id
    */
    private Integer productId;

    /**
    * 库存对应的商品规格sku-id
    */
    private Integer productSpecId;

    /**
    * 可售库存
    */
    private Integer availableStock;

    /**
    * 实际库存
    */
    private Integer totalStock;

    /**
    * 警告库存
    */
    private Integer warningStock;

    /**
    * 库存状态 0有库存1无库存
    */
    private Byte stockState;

    private static final long serialVersionUID = 1L;
}