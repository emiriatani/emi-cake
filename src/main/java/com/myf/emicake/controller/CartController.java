package com.myf.emicake.controller;

import com.myf.emicake.common.Result;
import com.myf.emicake.dto.CartItemDTO;
import com.myf.emicake.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/{memberId}")
    public Result addItemToCart(@PathVariable("memberId") String memberId,
                                @RequestBody CartItemDTO cartItemDTO
                               ){
        cartService.addToCart(memberId, cartItemDTO);

        return null;
    }

}
