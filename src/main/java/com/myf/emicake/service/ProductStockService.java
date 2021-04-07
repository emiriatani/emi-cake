package com.myf.emicake.service;

import com.myf.emicake.domain.ProductStock;
public interface ProductStockService{


    int deleteByPrimaryKey(Integer id);

    int insert(ProductStock record);

    int insertSelective(ProductStock record);

    ProductStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductStock record);

    int updateByPrimaryKey(ProductStock record);

    ProductStock selectByProductSkuId(Integer productSkuId);

    /**
     *
     */


}
