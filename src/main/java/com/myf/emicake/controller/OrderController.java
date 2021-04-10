package com.myf.emicake.controller;

import com.myf.emicake.common.Result;
import com.myf.emicake.common.StatusCode;
import com.myf.emicake.component.Producter;
import com.myf.emicake.component.properties.RabbitMqMsgProperties;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private Producter producter;
    @Autowired
    private RabbitMqMsgProperties rabbitMqMsgProperties;

    @RequestMapping("/toDeal")
    public Result toDeal(@RequestBody @Validated CartDTO cartDTO) {

        if (!ObjectUtils.isEmpty(cartDTO)) {
            return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg());
        }

        return ResultUtils.error(StatusCode.UNKNOWN_ERROR.getCode(), StatusCode.UNKNOWN_ERROR.getMsg());

    }

    @RequestMapping("/add")
    public Result generateOrder(@RequestBody OrderDTO orderDTO) throws InvocationTargetException, IllegalAccessException {

        //1.检验商品状态(库存,有效,上下架)
        Map<Integer, Integer> map = orderService.isValid(orderDTO);

        if (map.isEmpty()) {
            /*有库存,生成订单*/
            Order order = orderService.generateOrder(orderDTO);
            int i = orderService.insertSelective(order);
            if (i > 0) {
                log.info("订单保存成功...");
                /*
                一、异步通知Mq来锁定库存
                */
                Order fullOrder = orderService.selectByOrderId(order.getOrderId());
                System.out.println("创建完成的订单信息：" + fullOrder);
                /*通过MQ发送消息，
                设置订单的超时时间，
                若超时会自动取消订单，并回滚锁定的库存*/
                //producter.sendMessage(rabbitMqMsgProperties.getTopicExchangeName(), rabbitMqMsgProperties.getOrderRoutekey(), fullOrder);
                /*返回创建成功的订单给前端*/
                return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg(), fullOrder);
            } else {
                /*订单创建失败*/
                return ResultUtils.error(StatusCode.ORDER_CREATE_ERROR.getCode(), StatusCode.ORDER_CREATE_ERROR.getMsg());
            }
        } else {
            if (map.containsKey(1)) {
                ProductSku productSku = productSkuService.selectByPrimaryKey(map.get(1));
            }
            if (map.containsKey(2)) {
                ProductSku productSku = productSkuService.selectByPrimaryKey(map.get(2));
            }
            if (map.containsKey(3)) {
                ProductSku productSku = productSkuService.selectByPrimaryKey(map.get(3));
            }
            if (map.containsKey(4)) {
                ProductSku productSku = productSkuService.selectByPrimaryKey(map.get(4));
            }
            //给前端返回订单中商品库存不足或无效的信息.
            return ResultUtils.error(StatusCode.NO_STOCK.getCode(), StatusCode.NO_STOCK.getMsg());
        }

    }

    @RequestMapping("/get")
    public Result getOrder(@RequestParam("orderId")Integer orderId){

        Order order = orderService.selectByPrimaryKey(orderId);

        if (!ObjectUtils.isEmpty(order)){
            return ResultUtils.success(StatusCode.REQUEST_SUCCESS.getCode(), StatusCode.REQUEST_SUCCESS.getMsg(), order);
        }

        return ResultUtils.error(StatusCode.UNKNOWN_ERROR.getCode(), StatusCode.UNKNOWN_ERROR.getMsg());

    }


}
