package com.myf.emicake.service;

import com.myf.emicake.domain.Shop;
import com.myf.emicake.dto.ShopDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ShopService{


    int deleteByPrimaryKey(Integer id);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

    List<ShopDTO> getAllShop() throws InvocationTargetException, IllegalAccessException;

}
