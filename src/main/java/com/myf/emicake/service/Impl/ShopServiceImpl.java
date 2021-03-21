package com.myf.emicake.service.Impl;

import com.myf.emicake.domain.Shop;
import com.myf.emicake.dto.ShopDTO;
import com.myf.emicake.mapper.ShopMapper;
import com.myf.emicake.service.ShopService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Resource
    private ShopMapper shopMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return shopMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Shop record) {
        return shopMapper.insert(record);
    }

    @Override
    public int insertSelective(Shop record) {
        return shopMapper.insertSelective(record);
    }

    @Override
    public Shop selectByPrimaryKey(Integer id) {
        return shopMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Shop record) {
        return shopMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Shop record) {
        return shopMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ShopDTO> getAllShop() throws InvocationTargetException, IllegalAccessException {

        List<ShopDTO> shopDTOList = new LinkedList<>();
        List<Shop> allShop = shopMapper.getAllShop();

        for (Shop s :
                allShop) {
            ShopDTO shopDTO = new ShopDTO();
            BeanUtils.copyProperties(shopDTO, s);
            shopDTOList.add(shopDTO);
        }

        return shopDTOList;
    }

}
