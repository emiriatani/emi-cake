package com.myf.emicake.service;

import com.myf.emicake.domain.Cart;
import com.myf.emicake.dto.CartDTO;
import com.myf.emicake.dto.CartItemDTO;

public interface CartService {



    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    boolean addToCart(String memberId, CartItemDTO cartItemDTO);

    CartDTO getCart(String memberId);

    void updateCartItemNumber(String memberId,CartItemDTO cartItemDTO);

    boolean deleteCartItem(String memberId,CartItemDTO cartItemDTO);

    boolean deleteAllCartItem(String memberId);

    Cart DTOToCart(CartDTO cartDTO,CartItemDTO cartItemDTO);

}

