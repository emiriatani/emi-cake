package com.myf.emicake.mapper;

import com.myf.emicake.domain.OrderPickup;

public interface OrderPickupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderPickup record);

    int insertSelective(OrderPickup record);

    OrderPickup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderPickup record);

    int updateByPrimaryKey(OrderPickup record);
}