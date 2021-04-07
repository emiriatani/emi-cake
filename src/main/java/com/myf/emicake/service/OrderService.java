package com.myf.emicake.service;

import com.myf.emicake.domain.Order;
import com.myf.emicake.dto.OrderDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface OrderService{


    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

   /*
   * 判断订单中的商品是否有库存、是否有效、是否在上架状态
   *
   * 返回值
   * map<0,x> 所有商品都有库存
   * map<1,x> 订单中该商品超出购买库存，x为该超出库存的商品SkuId
   * map<2,x> 订单中该商品无库存，x为无库存的商品SkuId
   * map<3,x> 订单中该商品已失效,x为已失效的商品skuId
   * map<4,x> 订单中该商品已经下架,x为已下架的商品skuId
   * */
    Map<Integer, Integer> isValid(OrderDTO orderDTO);


    /**
     * 生成订单
     * @param orderDTO
     * @return
     */
    Order generateOrder(OrderDTO orderDTO) throws InvocationTargetException, IllegalAccessException;

    /**
     * 通过订单编号查询订单信息
     * @param orderId
     * @return
     */
    Order selectByOrderId(String orderId);
}
