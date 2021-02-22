package com.myf.emicake.service.Impl;

import cn.hutool.json.JSONUtil;
import com.myf.emicake.domain.ProductSku;
import com.myf.emicake.dto.ProductSkuDTO;
import com.myf.emicake.mapper.ProductSkuMapper;
import com.myf.emicake.service.ProductSkuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSkuServiceImpl implements ProductSkuService{


    @Resource
    private ProductSkuMapper productSkuMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return productSkuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ProductSku record) {
        return productSkuMapper.insert(record);
    }

    @Override
    public int insertSelective(ProductSku record) {
        return productSkuMapper.insertSelective(record);
    }

    @Override
    public ProductSku selectByPrimaryKey(Integer id) {
        return productSkuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ProductSku record) {
        return productSkuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ProductSku record) {
        return productSkuMapper.updateByPrimaryKey(record);
    }


    /**
     * 根据商品(SPU)id查询该商品id下所有规格的商品(SKU),
     * 并封装为List<ProductSkuDTO>
     * @param id
     * @return
     */
    @Override
    public List<ProductSkuDTO> selectByProductId(Integer id) {

        List<ProductSkuDTO> list = new ArrayList<>();
        List<ProductSku> prodSkuList = productSkuMapper.selectByProductId(id);

        /*忽略null字段，List<Product1>转Json*/
        String prodJsonStr = JSONUtil.toJsonStr(prodSkuList);
        /*Json转List<ProductSkuDTO>*/
        List<ProductSkuDTO> prodSkuDTOList = JSONUtil.toList(prodJsonStr, ProductSkuDTO.class);

        return prodSkuDTOList;
    }

}
