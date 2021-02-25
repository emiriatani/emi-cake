package com.myf.emicake;

import cn.hutool.json.JSONUtil;
import com.myf.emicake.domain.Product;
import com.myf.emicake.dto.BannerDTO;
import com.myf.emicake.dto.ProductDTO;
import com.myf.emicake.mapper.ProductMapper;
import com.myf.emicake.service.MemberService;
import com.myf.emicake.service.ProductService;
import com.myf.emicake.utils.JSONUtils;
import com.myf.emicake.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class MyprojectApplicationTests {

    @Autowired
    private JSONUtils jsonUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Test
    void contextLoads() {

        List<Product> list = productMapper.selectInBanner(2);
        List<BannerDTO> bannerDTOList = new ArrayList<>();

        System.out.println(list);

        String s = JSONUtil.toJsonStr(list);
        System.out.println(s);

        List<BannerDTO> bannerDTOList1 = JSONUtil.toList(s, BannerDTO.class);

        System.out.println(bannerDTOList1);

    }

    @Test
    public void test01() throws InvocationTargetException, IllegalAccessException {
        Product product = productService.selectByPrimaryKey(1);
        ProductDTO productDTO = new ProductDTO();

        BeanUtils.copyProperties(productDTO, product);

        System.out.println(productDTO);
    }


    @Test
    public void test02() throws InvocationTargetException, IllegalAccessException {

    }
    
    


}
