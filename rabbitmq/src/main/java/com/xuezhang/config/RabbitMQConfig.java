package com.xuezhang.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @description:
 * @author: 学长
 * @date: 2021/3/8 15:31
 */
@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue test(){
        return new Queue("Queue1");
    }
}
