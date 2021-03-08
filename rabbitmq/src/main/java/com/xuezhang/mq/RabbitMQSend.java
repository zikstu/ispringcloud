package com.xuezhang.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 学长
 * @date: 2021/3/8 16:38
 */
@Component
public class RabbitMQSend {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        amqpTemplate.convertAndSend("Queue1", "这是一条测试信息！");
    }
}
