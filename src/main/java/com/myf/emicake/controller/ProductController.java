package com.myf.emicake.controller;

import com.myf.emicake.common.Constants;
import com.myf.emicake.common.Result;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.domain.Product;
import com.myf.emicake.dto.BannerDTO;
import com.myf.emicake.dto.ProductDTO;
import com.myf.emicake.dto.ProductSkuDTO;
import com.myf.emicake.service.ProductService;
import com.myf.emicake.service.ProductSkuService;
import com.myf.emicake.utils.RedisUtils;
import com.myf.emicake.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @ClassName com.myf.emicake.controller ProductController
 * @Description
 * @Author Afengis
 * @Date 2021/2/19 14:16
 * @Version V1.0
 **/
@Slf4j
@Controller
@RequestMapping("/prod")
public class ProductController {


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSkuService productSkuService;

    @ResponseBody
    @GetMapping("/banner/{bannerCount}")
    public Result getBannerList(@PathVariable("bannerCount") Integer total) {
        System.out.println(total);
        List<BannerDTO> list = null;
        if (redisUtils.exists(Constants.BANNER_LIST_KEY)) {
            list = redisUtils.get(Constants.BANNER_LIST_KEY);
            log.info("从redis中取出的banner数据" + list);
        } else {
            list = productService.selectInBanner(total);
            redisUtils.set(Constants.BANNER_LIST_KEY, list);
        }
        return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(),StatusCode.REQUEST_SUCCESS.getMsg(),list);
    }

    @ResponseBody
    @GetMapping("/{productId}")
    public Result<Object> getProduct(@PathVariable("productId") Integer productId) throws InvocationTargetException, IllegalAccessException {

        Product product = productService.selectByPrimaryKey(productId);
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(productDTO, product);
        System.out.println(product);

        return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg(), productDTO);
    }

    @ResponseBody
    @GetMapping("/sku/{productId}")
    public Result getProductSpec(@PathVariable("productId") Integer productId){

        List<ProductSkuDTO> list = productSkuService.selectByProductId(productId);

        return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg(), list);
    }
}
