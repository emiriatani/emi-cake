package com.myf.emicake.service.Impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.myf.emicake.mapper.OrderPickupMapper;
import com.myf.emicake.domain.OrderPickup;
import com.myf.emicake.service.OrderPickupService;
@Service
public class OrderPickupServiceImpl implements OrderPickupService{

    @Resource
    private OrderPickupMapper orderPickupMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return orderPickupMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OrderPickup record) {
        return orderPickupMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderPickup record) {
        return orderPickupMapper.insertSelective(record);
    }

    @Override
    public OrderPickup selectByPrimaryKey(Integer id) {
        return orderPickupMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderPickup record) {
        return orderPickupMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderPickup record) {
        return orderPickupMapper.updateByPrimaryKey(record);
    }

}
