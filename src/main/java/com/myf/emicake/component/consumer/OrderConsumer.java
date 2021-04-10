package com.myf.emicake.component.consumer;

import com.myf.emicake.domain.Order;
import com.myf.emicake.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName com.myf.emicake.component.consumer OrderConsumer
 * @Description
 * @Author Afengis
 * @Date 2021/4/8 12:23
 * @Version V1.0
 **/
@Component
@Slf4j
public class OrderConsumer {

    @Autowired
    private OrderService orderService;

//    /**
//     * 监听正常订单队列
//     * @param order
//     * @param message
//     * @param channel
//     */
//    @RabbitHandler
//    @RabbitListener(queues = "msg.order.deal")
//    public void listenOrder(Order order, Message message, Channel channel) {
//        //得到消息唯一标识
//        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//
//        try {
//            //1. 接收转换消息
//            log.info("订单创建成功,等待支付中...该订单编号为:" + order.getOrderId());
//            log.info("订单消息主体内容:" + new String(message.getBody()));
//
//            //不签收，使之成为死信消息
//            //channel.basicAck(deliveryTag, false);
//        } catch (Exception e) {
//            log.error("出现异常，拒绝签收消息", e);
//            //4.拒绝签收，不重回队列 requeue=false
//            try {
//                channel.basicNack(deliveryTag, true, false);
//            } catch (IOException ex) {
//                log.info("拒绝签收消息失败");
//                ex.printStackTrace();
//            }
//        }
//
//    }

    /**
     * 处理超时的订单
     *
     * @param order
     * @param message
     * @param channel
     */
    @RabbitHandler
    @RabbitListener(queues = "msg.order.dead.deal")
    public void handleExpiredOrder(Order order, Message message, Channel channel) {

        //得到消息唯一标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            //1. 接收转换消息
            log.info("超时订单处理,该超时订单编号为:" + order.getOrderId());
            log.info("订单消息主体内容:" + new String(message.getBody()));
            //2.处理超时的订单,解锁库存
            order.setOrderStatus((byte) 2);
            order.setOrderSettlementStatus((byte) 2);
            int update = orderService.updateByPrimaryKeySelective(order);
            if (update > 0) {
                /*取消订单成功，解锁库存，回滚库存*/
                System.out.println("取消订单成功...");
            } else {

            }
            //3. 手动签收
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("出现异常，拒绝签收消息", e);
            //4.拒绝签收，不重回队列 requeue=false
            try {
                channel.basicNack(deliveryTag, true, false);
            } catch (IOException ex) {
                log.info("拒绝签收死信消息失败");
                ex.printStackTrace();
            }
        }
    }
}
