package com.myf.emicake.service.Impl;

import com.myf.emicake.common.Constants;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.domain.Cart;
import com.myf.emicake.dto.CartDTO;
import com.myf.emicake.dto.CartItemDTO;
import com.myf.emicake.exception.GlobalException;
import com.myf.emicake.mapper.CartMapper;
import com.myf.emicake.service.CartService;
import com.myf.emicake.service.ProductSkuService;
import com.myf.emicake.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Slf4j
@Service

public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ProductSkuService productSkuService;



    @Override
    public int deleteByPrimaryKey(Integer id) {
        return cartMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Cart record) {
        return cartMapper.insert(record);
    }

    @Override
    public int insertSelective(Cart record) {
        return cartMapper.insertSelective(record);
    }

    @Override
    public Cart selectByPrimaryKey(Integer id) {
        return cartMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Cart record) {
        return cartMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Cart record) {
        return cartMapper.updateByPrimaryKey(record);
    }




    /**
     * 添加商品到购物车
     * @param memberId 登录状态下用户id
     * @param cartItemDTO 购物车中每个商品项对应的数据模型
     * @return
     */
    @Override
    public boolean addToCart(String memberId, CartItemDTO cartItemDTO) {

        log.info("会员id：" + memberId);
        if (!StringUtils.isEmpty(cartItemDTO)){
            cartItemDTO.setTotalPrice();
        }
        System.out.println("加入到购物车的商品项信息:" + cartItemDTO);
        if (Integer.parseInt(memberId) == Constants.NO_LOGIN_MEMBER_FLAG){
            throw  new GlobalException(StatusCode.NOT_LOGIN_ACCESS.getCode(),StatusCode.NOT_LOGIN_ACCESS.getMsg());
        }else {
            /*判断该商品项在redis中是否存在*/
            boolean hasItem = redisUtils.hexists(Constants.CART_KEY_PREFIX + memberId, cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId());

            if (hasItem){
                CartItemDTO existedCartItem = redisUtils.hget(Constants.CART_KEY_PREFIX + memberId, cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId());
                existedCartItem.setNumber(existedCartItem.getNumber() + cartItemDTO.getNumber());
                redisUtils.hset(Constants.CART_KEY_PREFIX + memberId, cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId(), existedCartItem);

            }else {
                redisUtils.hset(Constants.CART_KEY_PREFIX + memberId, cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId(), cartItemDTO);
            }

        }

        return false;
    }

    /**
     * 查询整个购物车
     * @param memberId
     * @return
     */
    @Override
    public CartDTO getCart(String memberId) {
        return null;
    }

    /**
     * 更新购物车中商品的购买数量
     * @param memberId
     * @param cartItemDTO
     */
    @Override
    public void updateCartItemNumber(String memberId, CartItemDTO cartItemDTO) {

    }

    /**
     * 删除购物车中的一个商品项
     * @param memberId
     * @param cartItemDTO
     * @return
     */
    @Override
    public boolean deleteCartItem(String memberId, CartItemDTO cartItemDTO) {
        return false;
    }

    /**
     * 清空购物车
     * @param memberId
     * @return
     */
    @Override
    public boolean deleteAllCartItem(String memberId) {
        return false;
    }

    /**
     * 把购物车及其商品信息封装为Cart,以方便存入数据库表中
     * @param cartDTO
     * @param cartItemDTO
     * @return
     */
    @Override
    public Cart DTOToCart(CartDTO cartDTO, CartItemDTO cartItemDTO) {
        return null;
    }

}
