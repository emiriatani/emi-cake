package com.myf.emicake.component.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName com.myf.emicake.config.rabbitmq RabbitMqMsgProperties
 * @Description  rabbitmq属性配置类
 * @Author Afengis
 * @Date 2021/2/27 13:57
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RabbitMqMsgProperties {
    /*普通topic交换机*/
    @Value("${rabbit.msg.exchange.topic.name}")
    private String topicExchangeName;
    /*死信交换机*/
    @Value("${rabbit.msg.exchange.dead.name}")
    private String deadExchangeName;

    @Value("${rabbit.msg.queue.dao.name}")
    private String daoQueueName;

    @Value("${rabbit.msg.queue.dao.dead.name}")
    private String daoDeadQueueName;

    @Value("${rabbit.msg.queue.sms.name}")
    private String smsQueueName;

    @Value("${rabbit.msg.queue.sms.dead.name}")
    private String smsDeadQueueName;

    @Value("${rabbit.msg.queue.order.name}")
    private String orderQueueName;

    @Value("${rabbit.msg.queue.order.dead.name}")
    private String orderDeadQueueName;

    @Value("${rabbit.msg.route.dao}")
    private String daoRoutekey;

    @Value("${rabbit.msg.route.sms}")
    private String smsRoutekey;

    @Value("${rabbit.msg.route.order}")
    private String orderRoutekey;
}
