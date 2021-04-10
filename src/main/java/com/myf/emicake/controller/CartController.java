package com.myf.emicake.controller;

import com.myf.emicake.common.Constants;
import com.myf.emicake.common.Result;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.dto.CartDTO;
import com.myf.emicake.dto.CartItemDTO;
import com.myf.emicake.dto.MemberDTO;
import com.myf.emicake.exception.GlobalException;
import com.myf.emicake.service.CartService;
import com.myf.emicake.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @PostMapping("/add")
    public Result addItemToCart(@RequestBody CartItemDTO cartItemDTO){
        boolean addResult = cartService.addToCart(cartItemDTO);
        if (addResult){
            return ResultUtils.success(StatusCode.ADD_CART_SUCCESS.getCode(), StatusCode.ADD_CART_SUCCESS.getMsg());
        }
        return ResultUtils.error(StatusCode.ADD_CART_FAIL.getCode(), StatusCode.ADD_CART_FAIL.getMsg());

    }


    @GetMapping("/get")
    public Result getCart(){
        CartDTO cart = cartService.getCart();
        return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg(), cart);

    }

    @GetMapping("/toCartPage")
    public Result toCartPage(HttpSession httpSession){

        Subject subject = SecurityUtils.getSubject();
        MemberDTO sessionMemberDTO = (MemberDTO) httpSession.getAttribute(Constants.LOGIN_MEMBER_KEY);
        MemberDTO memberDTO = (MemberDTO) subject.getSession().getAttribute(Constants.LOGIN_MEMBER_KEY);
        System.out.println(memberDTO);
        if (!(ObjectUtils.isEmpty(memberDTO)|| ObjectUtils.isEmpty(sessionMemberDTO)) && memberDTO.equals(sessionMemberDTO)){
            return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg());
        }else {
            throw  new GlobalException(StatusCode.NOT_LOGIN_ACCESS.getCode(), StatusCode.NOT_LOGIN_ACCESS.getMsg());
        }
    }

    @PostMapping("/update")
    public Result updateCart(@RequestBody CartItemDTO cartItemDTO) throws InvocationTargetException, IllegalAccessException {

        boolean updateResult = cartService.updateCartItemNumber(cartItemDTO);

        if (updateResult) {
            return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg());
        }else {
            return ResultUtils.error(StatusCode.UPDATE_NUMBER_ERROR.getCode(), StatusCode.UPDATE_NUMBER_ERROR.getMsg());
        }
    }

    @PostMapping("/delete")
    public Result deleteCartItem(@RequestBody CartItemDTO cartItemDTO){
        boolean deleteResult = cartService.deleteCartItem(cartItemDTO);
        if (deleteResult){
            log.info("删除购物车商品项成功");
            return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg());
        }else {
            log.info("删除购物车商品项失败");
            return ResultUtils.error(StatusCode.DELETE_ERROR.getCode(), StatusCode.DELETE_ERROR.getMsg());
        }
    }

    @PostMapping("/deleteAll")
    public Result deleteCart(){
        boolean deleteResult = cartService.deleteAllCartItem();
        if (deleteResult){
            return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg());
        }else {
            return ResultUtils.error(StatusCode.DELETE_ALL_ERROR.getCode(), StatusCode.DELETE_ALL_ERROR.getMsg());
        }
    }


}
