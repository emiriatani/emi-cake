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

    boolean addToCart(CartItemDTO cartItemDTO);

    CartDTO getCart();

    boolean updateCartItemNumber(CartItemDTO cartItemDTO) throws InvocationTargetException, IllegalAccessException;

    boolean deleteCartItem(CartItemDTO cartItemDTO);

    boolean deleteAllCartItem();



}

