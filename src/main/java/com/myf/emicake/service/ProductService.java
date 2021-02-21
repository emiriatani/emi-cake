package com.myf.emicake.service;

import com.myf.emicake.domain.Product;
import com.myf.emicake.dto.BannerDTO;

import java.util.List;

public interface ProductService{


    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<BannerDTO> selectInBanner(int total);

    int selectInBannerTotal();
}
