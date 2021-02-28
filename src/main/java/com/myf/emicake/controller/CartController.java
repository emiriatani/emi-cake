package com.myf.emicake.controller;

import com.myf.emicake.common.Result;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.dto.CartDTO;
import com.myf.emicake.dto.CartItemDTO;
import com.myf.emicake.service.CartService;
import com.myf.emicake.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName com.myf.emicake.controller CartController
 * @Description
 * @Author Afengis
 * @Date 2021/2/25 14:40
 * @Version V1.0
 **/
@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping("/add/{memberId}")
    public Result addItemToCart(@PathVariable("memberId") String memberId,
                                @RequestBody CartItemDTO cartItemDTO
                               ){
        boolean result = cartService.addToCart(memberId, cartItemDTO);

        if (result){
            return ResultUtils.success(StatusCode.ADD_CART_SUCCESS.getCode(), StatusCode.ADD_CART_SUCCESS.getMsg());
        }
        return ResultUtils.error(StatusCode.ADD_CART_FAIL.getCode(), StatusCode.ADD_CART_FAIL.getMsg());

    }


    @GetMapping("/get/{memberId}")
    public Result getCart(@PathVariable("memberId") String memberId){

        CartDTO cart = cartService.getCart(memberId);
        return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg(), cart);

    }

    @PostMapping("/update/{memberId}")
    public Result updateCart(@PathVariable("memberId") String memberId,
                             @RequestBody CartItemDTO cartItemDTO) throws InvocationTargetException, IllegalAccessException {

        boolean updateResult = cartService.updateCartItemNumber(memberId, cartItemDTO);

        if (updateResult) {
            return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg());
        }else {

        }
        return null;
    }


}
