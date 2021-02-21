package com.myf.emicake.mapper;

import com.myf.emicake.domain.ProductSku;

import java.util.List;

public interface ProductSkuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductSku record);

    int insertSelective(ProductSku record);

    ProductSku selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductSku record);

    int updateByPrimaryKey(ProductSku record);

    /**
     * 根据商品(SPU)id查询该商品id下所有规格的商品(SKU)
     * @param id
     * @return
     */
    List<ProductSku> selectByProductId(Integer id);

}