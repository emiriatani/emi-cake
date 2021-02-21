package com.myf.emicake.mapper;

import com.myf.emicake.domain.Product;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    /**
     * 查找product表中banner_status为1的所有记录
     * @param total limit的条件
     * @return
     */
    List<Product> selectInBanner(int total);

    /**
     * 查找product表中banner_status为1的所有记录的数量
     * @return
     */
    int selectInBannerTotal();


}