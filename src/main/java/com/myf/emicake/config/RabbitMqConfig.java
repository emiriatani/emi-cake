package com.myf.emicake.config;

import com.myf.emicake.component.MyRabbitMqCallBack;
import com.myf.emicake.component.properties.RabbitMqMsgProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @ClassName com.myf.emicake.config.rabbitmq RabbitMqConfig
 * @Description
 * @Author Afengis
 * @Date 2021/2/27 14:08
 * @Version V1.0
 **/
@Configuration
@ConditionalOnBean(value = RabbitMqMsgProperties.class)
public class RabbitMqConfig {

    @Resource
    private RabbitMqMsgProperties rabbitMqMsgProperties;

    @Autowired
    private MyRabbitMqCallBack myRabbitMqCallBack;

    /*定义普通topic交换机*/
    @Bean
    public Exchange topicExchange(){
        return ExchangeBuilder.topicExchange(rabbitMqMsgProperties.getTopicExchangeName()).build();
    }

    /*定义死信交换机*/
    @Bean
    public Exchange deadExchange(){
        return ExchangeBuilder.topicExchange(rabbitMqMsgProperties.getDeadExchangeName()).build();
    }


    /*定义操作数据库普通队列*/
    @Bean
    public Queue daoQueue(){
        return QueueBuilder.durable(rabbitMqMsgProperties.getDaoQueueName())
                .withArgument("x-dead-letter-exchange",rabbitMqMsgProperties.getDeadExchangeName())//绑定死信交换机
                .withArgument("x-dead-letter-routing-key",rabbitMqMsgProperties.getDaoRoutekey())//绑定routingKey
                .withArgument("x-message-ttl",10000)//给这个队列添加过期时间 测试就使用10秒过期时间
                .build();
    }

    /*定义短信消息普通队列*/
    @Bean
    public Queue smsQueue(){
        return QueueBuilder.durable(rabbitMqMsgProperties.getSmsQueueName())
                .withArgument("x-dead-letter-exchange",rabbitMqMsgProperties.getDeadExchangeName())//绑定死信交换机
                .withArgument("x-dead-letter-routing-key",rabbitMqMsgProperties.getSmsRoutekey())//绑定routingKey
                .withArgument("x-message-ttl",10000)//给这个队列添加过期时间 测试就使用10秒过期时间
                .build();
    }

    /*定义订单消息普通队列*/
    @Bean
    public Queue orderQueue(){
        return QueueBuilder.durable(rabbitMqMsgProperties.getOrderQueueName())
                .withArgument("x-dead-letter-exchange",rabbitMqMsgProperties.getDeadExchangeName())//绑定死信交换机
                .withArgument("x-dead-letter-routing-key",rabbitMqMsgProperties.getOrderRoutekey())//绑定routingKey
                .withArgument("x-message-ttl",10000)//给这个队列添加过期时间 测试就使用10秒过期时间
                .build();
    }

    /*定义操作数据库的死信队列*/
    @Bean
    public Queue daoDeadQueue(){
        return QueueBuilder.durable(rabbitMqMsgProperties.getDaoDeadQueueName()).build();
    }
    /*定义短信消息的死信队列*/
    @Bean
    public Queue smsDeadQueue(){
        return QueueBuilder.durable(rabbitMqMsgProperties.getSmsDeadQueueName()).build();
    }
    /*定义订单消息的死信队列*/
    @Bean
    public Queue orderDeadQueue(){
        return QueueBuilder.durable(rabbitMqMsgProperties.getOrderDeadQueueName()).build();
    }

    /*将普通交换机与普通队列绑定*/
    @Bean
    public Binding daoNormalBinding(@Qualifier("topicExchange") Exchange exchange, @Qualifier("daoQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(rabbitMqMsgProperties.getDaoRoutekey()).noargs();
    }
    @Bean
    public Binding smsNormalBinding(@Qualifier("topicExchange") Exchange exchange, @Qualifier("smsQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(rabbitMqMsgProperties.getSmsRoutekey()).noargs();
    }
    @Bean
    public Binding orderNormalBinding(@Qualifier("topicExchange") Exchange exchange, @Qualifier("orderQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(rabbitMqMsgProperties.getOrderRoutekey()).noargs();
    }

    /*将死信交换机与死信队列绑定*/
    @Bean
    public Binding daoDeadBinding(@Qualifier("deadExchange") Exchange exchange, @Qualifier("daoDeadQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(rabbitMqMsgProperties.getDaoRoutekey()).noargs();
    }
    @Bean
    public Binding smsDeadBinding(@Qualifier("deadExchange") Exchange exchange, @Qualifier("smsDeadQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(rabbitMqMsgProperties.getSmsRoutekey()).noargs();
    }
    @Bean
    public Binding orderDeadBinding(@Qualifier("deadExchange") Exchange exchange, @Qualifier("orderDeadQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(rabbitMqMsgProperties.getOrderRoutekey()).noargs();
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        /*使用json序列化时配置文件中的手动确认模式会失效，这里强制转换一下*/
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setConfirmCallback(myRabbitMqCallBack);
        rabbitTemplate.setReturnCallback(myRabbitMqCallBack);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

}
