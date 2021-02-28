package com.myf.emicake.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName com.myf.emicake.component Producter
 * @Description
 * @Author Afengis
 * @Date 2021/2/28 10:29
 * @Version V1.0
 **/
@Slf4j
@Component
public class Producter {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendMessage(String exchange,String routeKey,Object object){
        rabbitTemplate.convertAndSend(exchange,routeKey,object);
    }

}
