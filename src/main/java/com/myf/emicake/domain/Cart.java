package com.myf.emicake.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName com.myf.emicake.domain Cart
 * @Description
 * @Author Afengis
 * @Date 2021/2/23 22:04
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart<T> implements Serializable {
    private static final long serialVersionUID = 1460098552474758700L;

    /*会员用户id*/
    private Integer memberId;

    /*商品数量*/
    private int productCount;

    /*商品项*/
    private List<T> cartItemList;

    public void setProductCount() {
        this.productCount = cartItemList.size();
    }
}
