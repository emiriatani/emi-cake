package com.myf.emicake.service.Impl;

import com.myf.emicake.domain.ProductStock;
import com.myf.emicake.mapper.ProductStockMapper;
import com.myf.emicake.service.ProductStockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class ProductStockServiceImpl implements ProductStockService{

    @Resource
    private ProductStockMapper productStockMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return productStockMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ProductStock record) {
        return productStockMapper.insert(record);
    }

    @Override
    public int insertSelective(ProductStock record) {
        return productStockMapper.insertSelective(record);
    }

    @Override
    public ProductStock selectByPrimaryKey(Integer id) {
        return productStockMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ProductStock record) {
        return productStockMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ProductStock record) {
        return productStockMapper.updateByPrimaryKey(record);
    }

    @Override
    public ProductStock selectByProductSkuId(Integer productSkuId) {

        ProductStock productStock = productStockMapper.selectByProductSkuId(productSkuId);

        return productStock;
    }


}
