package com.myf.emicake.service;

import com.myf.emicake.domain.ProductSku;
import com.myf.emicake.dto.ProductSkuDTO;

import java.util.List;

public interface ProductSkuService{


    int deleteByPrimaryKey(Integer id);

    int insert(ProductSku record);

    int insertSelective(ProductSku record);

    ProductSku selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductSku record);

    int updateByPrimaryKey(ProductSku record);

    List<ProductSkuDTO> selectByProductId(Integer id);

}
