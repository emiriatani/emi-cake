package com.myf.emicake.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.myf.emicake.common.Constants;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.domain.Cart;
import com.myf.emicake.dto.CartDTO;
import com.myf.emicake.dto.CartItemDTO;
import com.myf.emicake.exception.GlobalException;
import com.myf.emicake.mapper.CartMapper;
import com.myf.emicake.service.CartService;
import com.myf.emicake.service.ProductSkuService;
import com.myf.emicake.utils.JSONUtils;
import com.myf.emicake.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private JSONUtils jsonUtils;
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
     *
     * @param memberId    登录状态下用户id
     * @param cartItemDTO 购物车中每个商品项对应的数据模型
     * @return
     */
    @Override
    public boolean addToCart(String memberId, CartItemDTO cartItemDTO) {

        log.info("会员id：" + memberId);
        boolean addFlag = false;

        if (StringUtils.isEmpty(memberId)) {
            throw new GlobalException(StatusCode.EMPTY_MEMBER_ID.getCode(), StatusCode.EMPTY_MEMBER_ID.getMsg());
        }
        if (!StringUtils.isEmpty(cartItemDTO)) {
            cartItemDTO.setTotalPrice(cartItemDTO.getPrice(), cartItemDTO.getNumber());
        } else {
            throw new GlobalException(StatusCode.EMPTY_PRODUCT_INFO.getCode(), StatusCode.EMPTY_PRODUCT_INFO.getMsg());
        }

        System.out.println("加入到购物车的商品项信息:" + cartItemDTO);
        if (Integer.parseInt(memberId) == Constants.NO_LOGIN_MEMBER_FLAG) {
            throw new GlobalException(StatusCode.NOT_LOGIN_ACCESS.getCode(), StatusCode.NOT_LOGIN_ACCESS.getMsg());
        } else {
            /*判断该商品项在redis中是否存在*/
            boolean hasItem = redisUtils.hexists(Constants.CART_KEY_PREFIX + memberId, cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId());

            if (hasItem) {
                /*查询出已经存在的商品项*/
                Object existedCartItemObj = redisUtils.hget(Constants.CART_KEY_PREFIX + memberId, cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId());
                /*手动类型转换，existedCartItemObj为LinkedHashMap*/
                CartItemDTO existedCartItem = jsonUtils.myValueTypeConvert(existedCartItemObj, new TypeReference<CartItemDTO>() {
                });
                /*与新添加的商品数量相加*/
                existedCartItem.setNumber(existedCartItem.getNumber() + cartItemDTO.getNumber());
                existedCartItem.setTotalPrice(existedCartItem.getPrice(), existedCartItem.getNumber());
                /*重新存入购物车中*/
                addFlag = redisUtils.hset(Constants.CART_KEY_PREFIX + memberId, cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId(), existedCartItem);
            } else {
                addFlag = redisUtils.hset(Constants.CART_KEY_PREFIX + memberId, cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId(), cartItemDTO);
            }
        }
        return addFlag;
    }

    /**
     * 查询整个购物车
     *
     * @param memberId
     * @return
     */
    @Override
    public CartDTO getCart(String memberId) {

        if (StringUtils.isEmpty(memberId)) {
            throw new GlobalException(StatusCode.EMPTY_MEMBER_ID.getCode(), StatusCode.EMPTY_MEMBER_ID.getMsg());
        }
        if (Integer.parseInt(memberId) == Constants.NO_LOGIN_MEMBER_FLAG) {
            throw new GlobalException(StatusCode.NOT_LOGIN_ACCESS.getCode(), StatusCode.NOT_LOGIN_ACCESS.getMsg());
        } else {

            CartDTO cartDTO = new CartDTO();
            List<CartItemDTO> cartItemDTOList = new ArrayList<>();
            cartDTO.setMemberId(Integer.parseInt(memberId));
            /*判断该会员用户的购物车中是否有商品*/
            boolean exists = redisUtils.exists(Constants.CART_KEY_PREFIX + memberId);
            if (exists) {
                Map<?, ?> cartMap = redisUtils.hmget(Constants.CART_KEY_PREFIX + memberId);
                for (Map.Entry<?, ?> cartItem :
                        cartMap.entrySet()) {
                    System.out.println("key:" + cartItem.getKey());
                    System.out.println("value:" + cartItem.getValue());
                    System.out.println("value:" + cartItem.getValue().getClass().getName());
                    System.out.println("----------");
                    CartItemDTO cartItemDTO = jsonUtils.myValueTypeConvert(cartItem.getValue(), new TypeReference<CartItemDTO>() {
                    });
                    cartItemDTOList.add(cartItemDTO);
                }
                cartDTO.setCartItemDTOList(cartItemDTOList);
                cartDTO.setSize();
                cartDTO.setTotalPrice();
                System.out.println(cartDTO);
            }
            return cartDTO;
        }

    }

    /**
     * 更新购物车中商品的购买数量
     *
     * @param memberId
     * @param cartItemDTO
     */
    @Override
    public boolean updateCartItemNumber(String memberId, CartItemDTO cartItemDTO) throws InvocationTargetException, IllegalAccessException {

        boolean updateFlag = false;

        if (StringUtils.isEmpty(memberId)) {
            throw new GlobalException(StatusCode.EMPTY_MEMBER_ID.getCode(), StatusCode.EMPTY_MEMBER_ID.getMsg());
        }
        if (StringUtils.isEmpty(cartItemDTO)) {
            throw new GlobalException(StatusCode.EMPTY_PRODUCT_INFO.getCode(), StatusCode.EMPTY_PRODUCT_INFO.getMsg());
        }

        if (Integer.parseInt(memberId) == Constants.NO_LOGIN_MEMBER_FLAG) {
            throw new GlobalException(StatusCode.NOT_LOGIN_ACCESS.getCode(), StatusCode.NOT_LOGIN_ACCESS.getMsg());
        } else {
            boolean hexists = redisUtils.hexists(Constants.CART_KEY_PREFIX + memberId, cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId());
            if (hexists) {
                Object getObject = redisUtils.hget(Constants.CART_KEY_PREFIX + memberId, cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId());
                CartItemDTO needUpdateCartItem = jsonUtils.myValueTypeConvert(getObject, new TypeReference<CartItemDTO>() {});
                BeanUtils.copyProperties(needUpdateCartItem, cartItemDTO);
                System.out.println("更新后的购物项" + needUpdateCartItem);
                updateFlag = redisUtils.hset(Constants.CART_KEY_PREFIX + memberId, cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId(), needUpdateCartItem);
            }else {
                throw new GlobalException(StatusCode.NOT_EXIST_IN_CART.getCode(), StatusCode.NOT_EXIST_IN_CART.getMsg());
            }
        }
        return  updateFlag;
    }

    /**
     * 删除购物车中的一个商品项
     *
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
     *
     * @param memberId
     * @return
     */
    @Override
    public boolean deleteAllCartItem(String memberId) {
        return false;
    }

    /**
     * 把购物车及其商品信息封装为Cart,以方便存入数据库表中
     *
     * @param cartDTO
     * @param cartItemDTO
     * @return
     */
    @Override
    public Cart DTOToCart(CartDTO cartDTO, CartItemDTO cartItemDTO) {
        return null;
    }

}
