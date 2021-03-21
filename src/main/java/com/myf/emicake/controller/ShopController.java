package com.myf.emicake.controller;

import com.myf.emicake.common.Result;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.dto.ShopDTO;
import com.myf.emicake.service.ShopService;
import com.myf.emicake.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @ClassName com.myf.emicake.controller ShopController
 * @Description
 * @Author Afengis
 * @Date 2021/3/21 16:53
 * @Version V1.0
 **/
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/all")
    public Result getAllShop() throws InvocationTargetException, IllegalAccessException {

        List<ShopDTO> allShop = shopService.getAllShop();

        if (!ObjectUtils.isEmpty(allShop)){
            return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg(),allShop);
        }

        return ResultUtils.error(StatusCode.UNKNOWN_ERROR.getCode(), StatusCode.UNKNOWN_ERROR.getMsg());

    }
}
