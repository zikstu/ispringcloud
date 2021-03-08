package com.xuezhang.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 学长
 * @date: 2021/3/8 16:40
 */
@Component
@RabbitListener(queues = {"Queue1"})
public class RabbitMQReceiver {
    @RabbitHandler
    public void process(String message){
        System.out.println("接收到：" + message);
    }
}
