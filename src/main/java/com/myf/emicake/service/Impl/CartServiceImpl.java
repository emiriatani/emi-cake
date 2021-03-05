package com.myf.emicake.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.myf.emicake.common.Constants;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.component.Producter;
import com.myf.emicake.component.properties.RabbitMqMsgProperties;
import com.myf.emicake.domain.Cart;
import com.myf.emicake.domain.ProductSku;
import com.myf.emicake.dto.CartDTO;
import com.myf.emicake.dto.CartItemDTO;
import com.myf.emicake.dto.MemberDTO;
import com.myf.emicake.exception.GlobalException;
import com.myf.emicake.mapper.CartMapper;
import com.myf.emicake.service.CartService;
import com.myf.emicake.service.ProductSkuService;
import com.myf.emicake.utils.JSONUtils;
import com.myf.emicake.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
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
    @Resource
    private RabbitMqMsgProperties rabbitMqMsgProperties;
    @Resource
    private Producter producter;


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
     * @param cartItemDTO 购物车中每个商品项对应的数据模型
     * @return
     */
    @Override
    public boolean addToCart(CartItemDTO cartItemDTO) {

        boolean addFlag = false;

        if (!ObjectUtils.isEmpty(cartItemDTO)) {
            cartItemDTO.setTotalPrice(cartItemDTO.getPrice(), cartItemDTO.getNumber());
        } else {
            throw new GlobalException(StatusCode.EMPTY_PRODUCT_INFO.getCode(), StatusCode.EMPTY_PRODUCT_INFO.getMsg());
        }

        System.out.println("加入到购物车的商品项信息:" + cartItemDTO);

        Subject subject = SecurityUtils.getSubject();
        MemberDTO member = (MemberDTO) subject.getSession().getAttribute(Constants.LOGIN_MEMBER_KEY);

        if (ObjectUtils.isEmpty(member)) {
            throw new GlobalException(StatusCode.NOT_LOGIN_ACCESS.getCode(), StatusCode.NOT_LOGIN_ACCESS.getMsg());
        } else {
            /*判断该商品项在redis中是否存在*/
            boolean hasItem = redisUtils.hexists(Constants.CART_KEY_PREFIX + member.getId(), cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId());
            if (hasItem) {
                /*查询出已经存在的商品项*/
                Object existedCartItemObj = redisUtils.hget(Constants.CART_KEY_PREFIX + member.getId(), cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId());
                /*手动类型转换，existedCartItemObj为LinkedHashMap*/
                CartItemDTO existedCartItem = jsonUtils.myValueTypeConvert(existedCartItemObj, new TypeReference<CartItemDTO>() {
                });
                /*与新添加的商品数量相加*/
                existedCartItem.setNumber(existedCartItem.getNumber() + cartItemDTO.getNumber());
                existedCartItem.setTotalPrice(existedCartItem.getPrice(), existedCartItem.getNumber());
                /*重新存入购物车中*/
                addFlag = redisUtils.hset(Constants.CART_KEY_PREFIX + member.getId(), cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId(), existedCartItem);
            } else {
                addFlag = redisUtils.hset(Constants.CART_KEY_PREFIX + member.getId(), cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId(), cartItemDTO);
                if (addFlag) {
                    /*同步到数据库中*/
                    producter.sendMessage(rabbitMqMsgProperties.getTopicExchangeName(), rabbitMqMsgProperties.getDaoRoutekey(), DTOToCart(member.getId(), cartItemDTO));
                }
            }
        }
        return addFlag;
    }

    /**
     * 查询整个购物车
     *
     * @param
     * @return
     */
    @Override
    public CartDTO getCart() {

        Subject subject = SecurityUtils.getSubject();
        MemberDTO member = (MemberDTO) subject.getSession().getAttribute(Constants.LOGIN_MEMBER_KEY);

        if (ObjectUtils.isEmpty(member)) {
            throw new GlobalException(StatusCode.NOT_LOGIN_ACCESS.getCode(), StatusCode.NOT_LOGIN_ACCESS.getMsg());
        } else {
            CartDTO cartDTO = new CartDTO();
            List<CartItemDTO> cartItemDTOList = new LinkedList<>();
            cartDTO.setMemberId(Integer.parseInt(String.valueOf(member.getId())));
            /*判断该会员用户的购物车中是否有商品*/
            boolean exists = redisUtils.exists(Constants.CART_KEY_PREFIX + member.getId());
            if (exists) {
                Map<?, ?> cartMap = redisUtils.hmget(Constants.CART_KEY_PREFIX + member.getId());
                for (Map.Entry<?, ?> cartItem :
                        cartMap.entrySet()) {
                    System.out.println("----------");
                    System.out.println("key:" + cartItem.getKey());
                    System.out.println("value:" + cartItem.getValue());
                    System.out.println("----------");
                    CartItemDTO cartItemDTO = jsonUtils.myValueTypeConvert(cartItem.getValue(), new TypeReference<CartItemDTO>() {
                    });
                    ProductSku productSku = productSkuService.selectByPrimaryKey(cartItemDTO.getProductSkuId());
                    if (cartItemDTO.getPrice() != productSku.getPrice()) {
                        if (cartItemDTO.getPrice().compareTo(productSku.getPrice()) == 1) {
                            /*商品价格比加入购物车时下降了*/
                            cartItemDTO.setPriceChangeFlag(1);
                            log.info("商品加入购物车时的价格:" + cartItemDTO.getPrice());
                            log.info("商品现在的价格:" + productSku.getPrice());
                            BigDecimal subtract = cartItemDTO.getPrice().subtract(productSku.getPrice());
                            log.info("商品下降了:" + subtract.toString());
                            cartItemDTO.setPriceChangeNumber(subtract);
                            cartItemDTO.setPrice(productSku.getPrice());
                            cartItemDTO.setTotalPrice(cartItemDTO.getPrice(), cartItemDTO.getNumber());
                        } else {
                            /*商品价格比加入购物车时上涨了*/
                            cartItemDTO.setPriceChangeFlag(2);
                            /*差价*/
                            log.info("商品加入购物车时的价格:" + cartItemDTO.getPrice());
                            log.info("商品现在的价格:" + productSku.getPrice());
                            BigDecimal subtract = productSku.getPrice().subtract(cartItemDTO.getPrice());
                            log.info("商品上涨了" + subtract.toString());
                            cartItemDTO.setPriceChangeNumber(subtract);
                            cartItemDTO.setPrice(productSku.getPrice());
                            cartItemDTO.setTotalPrice(cartItemDTO.getPrice(), cartItemDTO.getNumber());
                        }
                    } else {
                        /*价格没有发生变化*/
                        cartItemDTO.setPriceChangeFlag(0);
                    }
                    redisUtils.hset(Constants.CART_KEY_PREFIX + member.getId(), cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId(), cartItemDTO);
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
     * @param
     * @param cartItemDTO
     */
    @Override
    public boolean updateCartItemNumber(CartItemDTO cartItemDTO) throws InvocationTargetException, IllegalAccessException {

        boolean updateFlag = false;
        Subject subject = SecurityUtils.getSubject();
        MemberDTO member = (MemberDTO) subject.getSession().getAttribute(Constants.LOGIN_MEMBER_KEY);

        if (ObjectUtils.isEmpty(cartItemDTO)) {
            throw new GlobalException(StatusCode.EMPTY_PRODUCT_INFO.getCode(), StatusCode.EMPTY_PRODUCT_INFO.getMsg());
        }

        if (ObjectUtils.isEmpty(member)){
            throw new GlobalException(StatusCode.EMPTY_MEMBER_INFO.getCode(), StatusCode.EMPTY_MEMBER_INFO.getMsg());
        } else {
            boolean hexists = redisUtils.hexists(Constants.CART_KEY_PREFIX + member.getId(), cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId());
            if (hexists) {
                Object getObject = redisUtils.hget(Constants.CART_KEY_PREFIX + member.getId(), cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId());
                CartItemDTO needUpdateCartItem = jsonUtils.myValueTypeConvert(getObject, new TypeReference<CartItemDTO>() {
                });
                BeanUtils.copyProperties(needUpdateCartItem, cartItemDTO);
                needUpdateCartItem.setTotalPrice(needUpdateCartItem.getPrice(), needUpdateCartItem.getNumber());
                System.out.println("更新后的购物项" + needUpdateCartItem);
                updateFlag = redisUtils.hset(Constants.CART_KEY_PREFIX + member.getId(), cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId(), needUpdateCartItem);
            } else {
                throw new GlobalException(StatusCode.NOT_EXIST_IN_CART.getCode(), StatusCode.NOT_EXIST_IN_CART.getMsg());
            }
        }
        return updateFlag;
    }

    /**
     * 删除购物车中的一个商品项
     *
     * @param cartItemDTO
     * @return
     */
    @Override
    public boolean deleteCartItem(CartItemDTO cartItemDTO) {

        boolean flag = false;
        Subject subject = SecurityUtils.getSubject();
        MemberDTO member = (MemberDTO) subject.getSession().getAttribute(Constants.LOGIN_MEMBER_KEY);

        if (ObjectUtils.isEmpty(cartItemDTO)) {
            throw new GlobalException(StatusCode.EMPTY_PRODUCT_INFO.getCode(), StatusCode.EMPTY_PRODUCT_INFO.getMsg());
        }

        if (ObjectUtils.isEmpty(member)) {
            throw new GlobalException(StatusCode.EMPTY_MEMBER_INFO.getCode(), StatusCode.EMPTY_MEMBER_INFO.getMsg());
        } else {
            boolean hexists = redisUtils.hexists(Constants.CART_KEY_PREFIX + member.getId(), cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId());
            if (hexists) {
                redisUtils.hdel(Constants.CART_KEY_PREFIX + member.getId(), cartItemDTO.getProductId() + ":" + cartItemDTO.getProductSkuId());
                flag = true;
            } else {
                throw new GlobalException(StatusCode.NOT_EXIST_IN_CART.getCode(), StatusCode.NOT_EXIST_IN_CART.getMsg());
            }
        }
        return flag;
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @Override
    public boolean deleteAllCartItem() {

        boolean flag = false;
        Subject subject = SecurityUtils.getSubject();
        MemberDTO member = (MemberDTO) subject.getSession().getAttribute(Constants.LOGIN_MEMBER_KEY);

        if (ObjectUtils.isEmpty(member)) {
            throw new GlobalException(StatusCode.EMPTY_MEMBER_INFO.getCode(), StatusCode.EMPTY_MEMBER_INFO.getMsg());
        }else {
            boolean exists = redisUtils.exists(Constants.CART_KEY_PREFIX + member.getId());
            if (exists) {
                long remove = redisUtils.remove(Constants.CART_KEY_PREFIX + member.getId());
                if (remove != 0) {
                    flag = true;
                }
            } else {
                throw new GlobalException(StatusCode.NOT_EXIST_IN_CART.getCode(), StatusCode.NOT_EXIST_IN_CART.getMsg());
            }
        }
        return flag;
    }

    /**
     * @param memberId
     * @param cartItemDTO
     * @return
     */
    @Override
    public Cart DTOToCart(Integer memberId, CartItemDTO cartItemDTO) {
        Cart cart = new Cart();
        cart.setMemberId(memberId);
        cart.setItemId(cartItemDTO.getProductSkuId());
        cart.setQuantity(cartItemDTO.getNumber());
        cart.setSettlementStatus((byte) 0);
        cart.setCreateTime(LocalDateTime.now());
        return cart;
    }

}
