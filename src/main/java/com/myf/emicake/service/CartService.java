package com.myf.emicake.service;

import com.myf.emicake.domain.Cart;
import com.myf.emicake.dto.CartDTO;
import com.myf.emicake.dto.CartItemDTO;

import java.lang.reflect.InvocationTargetException;

public interface CartService {



    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    boolean addToCart(String memberId, CartItemDTO cartItemDTO);

    CartDTO getCart(String memberId);

    boolean updateCartItemNumber(String memberId,CartItemDTO cartItemDTO) throws InvocationTargetException, IllegalAccessException;

    boolean deleteCartItem(String memberId,CartItemDTO cartItemDTO);

    boolean deleteAllCartItem(String memberId);

    Cart DTOToCart(String memberId,CartItemDTO cartItemDTO);

}

