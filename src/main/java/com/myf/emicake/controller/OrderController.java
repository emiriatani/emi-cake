package com.myf.emicake.controller;

import com.myf.emicake.common.Result;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.domain.Order;
import com.myf.emicake.domain.ProductSku;
import com.myf.emicake.dto.CartDTO;
import com.myf.emicake.dto.OrderDTO;
import com.myf.emicake.service.AlipayService;
import com.myf.emicake.service.OrderService;
import com.myf.emicake.service.ProductSkuService;
import com.myf.emicake.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @ClassName com.myf.emicake.controller OrderController
 * @Description
 * @Author Afengis
 * @Date 2021/3/21 15:15
 * @Version V1.0
 **/
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private AlipayService alipayService;

    @RequestMapping("/toDeal")
    public Result toDeal(@RequestBody @Validated CartDTO cartDTO){

          if (!ObjectUtils.isEmpty(cartDTO)){
              return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg());
          }

        return ResultUtils.error(StatusCode.UNKNOWN_ERROR.getCode(), StatusCode.UNKNOWN_ERROR.getMsg());

    }

    @RequestMapping("/add")
    public Result generateOrder(@RequestBody OrderDTO orderDTO) throws InvocationTargetException, IllegalAccessException {

        //1.检验商品状态(库存,有效,上下架)
        Map<Integer, Integer> map = orderService.isValid(orderDTO);
        if (map.containsKey(0)){
            Order order = orderService.generateOrder(orderDTO);
            int i = orderService.insertSelective(order);
            if (i>0){

                /*
                异步通知Mq来锁定库存
                成功锁定后调用支付接口发起支付
                */


                /*
                支付接口调用成功后，
                把订单发送给MQ

                1.若30分钟未支付，进入死信队列处理，自动取消该订单(修改订单结算状态为已取消)

                ps:所有未成功支付的结果后需要回滚库存
                */



                /*
                支付成功后，
                异步通知MQ 更新库存，保存订单详情信息、自提订单信息/外卖配送订单信息

                 */
                log.info("订单保存成功...");
                log.info("开始调用支付接口...");
                Order fullOrder = orderService.selectByOrderId(order.getOrderId());



            }else {
                /*订单保存失败*/
            }
        }else {
            if (map.containsKey(1)){
                ProductSku productSku = productSkuService.selectByPrimaryKey(map.get(1));
            }
            if (map.containsKey(2)){
                ProductSku productSku = productSkuService.selectByPrimaryKey(map.get(2));
            }
            if (map.containsKey(3)){
                ProductSku productSku = productSkuService.selectByPrimaryKey(map.get(3));
            }
            if (map.containsKey(4)) {
                ProductSku productSku = productSkuService.selectByPrimaryKey(map.get(4));
            }
            //给前端返回生成订单失败的信息.
        }

        return null;
    }


}
