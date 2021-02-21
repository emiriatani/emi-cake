package com.myf.emicake.service.Impl;

import cn.hutool.json.JSONUtil;
import com.myf.emicake.domain.Product;
import com.myf.emicake.dto.BannerDTO;
import com.myf.emicake.mapper.ProductMapper;
import com.myf.emicake.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Product record) {
        return productMapper.insert(record);
    }

    @Override
    public int insertSelective(Product record) {
        return productMapper.insertSelective(record);
    }

    @Override
    public Product selectByPrimaryKey(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Product record) {
        return productMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Product record) {
        return productMapper.updateByPrimaryKey(record);
    }

    /**
     * 查找product表中banner_status为1的所有记录,
     * 并将结果封装成List<BannerDTO>
     * @param total limit的条件
     * @return
     */
    @Override
    public List<BannerDTO> selectInBanner(int total) {
        List<BannerDTO> list = new ArrayList<>();
        if (total < 0){
            return list;
        }
        List<Product> prodList = productMapper.selectInBanner(total);
        /*忽略null字段，List<Product>转Json*/
        String prodJsonStr = JSONUtil.toJsonStr(prodList);
        /*Json转List<BannerDTO>*/
        List<BannerDTO> bannerDTOList = JSONUtil.toList(prodJsonStr, BannerDTO.class);

        return bannerDTOList;
    }

    /**
     * 查找product表中banner_status为1的所有记录的数量
     * @return
     */
    @Override
    public int selectInBannerTotal() {
        return  productMapper.selectInBannerTotal();
    }
}
