package com.myf.emicake.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @ClassName com.myf.emicake.component MyRabbitMqCallBack
 * @Description
 * @Author Afengis
 * @Date 2021/2/27 19:52
 * @Version V1.0
 **/
@Slf4j
@Component
//使用CGLIB代理方式
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyRabbitMqCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    /**
     *
     * @param correlationData 相关配置信息
     * @param ack 表示交换机是否成功接收到了消息
     * @param cause 失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        log.info("发送消息后的回调函数正在执行中....,配置信息为:" + correlationData);

        if (ack){
            log.info("消息由生产者发送到交换机成功");
        }else {
            log.info("消息由生产者发送到交换机失败,失败原因为:" + cause);
        }
    }

    /**
     * 当Exchange路由到queue失败后,就会执行这个ReturnCallBack方法
     * @param message 消息对象
     * @param replyCode 错误码
     * @param replyText 错误信息
     * @param exchange 交换机
     * @param routeKey 路由键
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routeKey) {

        log.info("消息从交换机路由到队列失败，正在执行退回模式回调函数...");
        log.info("消息实体:" + message.toString());
        log.info("错误码:" + replyCode);
        log.info("错误信息:" + replyText);
        log.info("发送失败的交换机：" + exchange);
        log.info("发送失败的路由键:" + routeKey );

    }
}
