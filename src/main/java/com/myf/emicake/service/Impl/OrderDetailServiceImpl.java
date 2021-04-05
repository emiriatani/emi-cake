package com.myf.emicake.service.Impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.myf.emicake.mapper.OrderDetailMapper;
import com.myf.emicake.domain.OrderDetail;
import com.myf.emicake.service.OrderDetailService;
@Service
public class OrderDetailServiceImpl implements OrderDetailService{

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return orderDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OrderDetail record) {
        return orderDetailMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderDetail record) {
        return orderDetailMapper.insertSelective(record);
    }

    @Override
    public OrderDetail selectByPrimaryKey(Integer id) {
        return orderDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderDetail record) {
        return orderDetailMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderDetail record) {
        return orderDetailMapper.updateByPrimaryKey(record);
    }

}
