package com.myf.emicake.mapper;

import com.myf.emicake.domain.Shop;

import java.util.List;

public interface ShopMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

    List<Shop> getAllShop();

}