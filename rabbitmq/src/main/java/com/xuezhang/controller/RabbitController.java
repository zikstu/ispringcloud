package com.xuezhang.controller;

import com.xuezhang.mq.RabbitMQSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: 学长
 * @date: 2021/3/8 16:43
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitController {
    @Autowired
    private RabbitMQSend rabbitMQSend;

    @GetMapping("/receive")
    public String receive(){
        rabbitMQSend.send();
        return "ok";
    }
}
