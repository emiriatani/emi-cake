package com.myf.emicake.component.consumer;

import com.myf.emicake.dto.CartItemDTO;
import com.myf.emicake.mapper.CartMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName com.myf.emicake.component.consumer DAOConsumer
 * @Description 数据库相关操作的消费者
 * @Author Afengis
 * @Date 2021/2/27 19:21
 * @Version V1.0
 **/
@Slf4j
@Component

public class DAOConsumer {

    @Autowired
    private CartMapper cartMapper;

    @RabbitHandler
    @RabbitListener(queues = "msg.dao.update")
    public void onMessage(CartItemDTO cartItemDTO, Message message, Channel channel) throws Exception {

        //得到消息唯一标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            //1.接收转换消息

            log.info("消息主体内容:" + new String(message.getBody()));
            System.out.println(cartItemDTO);
            //2. 模拟处理业务逻辑
            System.out.println("延时测试...");
            Thread.sleep(11000);
            System.out.println("处理业务逻辑...");
            System.out.println("根据订单id查询其状态...");
            System.out.println("判断状态是否为支付成功");
            System.out.println("未支付,取消订单，回滚库存....");

            //3. 手动签收
            //channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
           log.error("出现异常，拒绝签收消息", e);
            //4.拒绝签收，不重回队列 requeue=false
            channel.basicNack(deliveryTag, true, false);
        }
    }


    @RabbitListener(queues = "msg.dao.dead.update")
    public void onDeadMessage(Message message, Channel channel) throws Exception {

        //得到消息唯一标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            //1.接收转换消息

            log.info("消息主体内容:" + new String(message.getBody()));

            //2. 模拟处理业务逻辑
            System.out.println("处理业务逻辑...");
            System.out.println("根据订单id查询其状态...");
            System.out.println("判断状态是否为支付成功");
            System.out.println("未支付,取消订单，回滚库存....");

            //3. 手动签收
            //channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            log.error("出现异常，拒绝签收消息", e);
            //4.拒绝签收，不重回队列 requeue=false
            channel.basicNack(deliveryTag, true, false);
        }
    }
}




