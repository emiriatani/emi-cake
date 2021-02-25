package com.myf.emicake.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName com.myf.emicake.dto CartDTO
 * @Description
 * @Author Afengis
 * @Date 2021/2/25 12:07
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO implements Serializable {

    private static final long serialVersionUID = -8546019102121921418L;

    private Integer id;

    /*会员id*/
    private Integer memberId;

    /*购物车商品项数量*/
    private int size;
    
    /*购物车商品项*/
    private List<CartItemDTO> cartItemDTOList;
    
    /*购物车总价*/
    private BigDecimal totalPrice;

    public void setSize() {
        this.size = getCartItemDTOList().size();
    }

    public void setTotalPrice() {
        List<CartItemDTO> cartItemDTOList = getCartItemDTOList();
        BigDecimal price = new BigDecimal("0");
        for (CartItemDTO item :
                cartItemDTOList) {
            price= price.add(item.getTotalPrice());
        }
        this.totalPrice = price;
    }
}
