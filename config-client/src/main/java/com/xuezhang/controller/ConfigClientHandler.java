package com.xuezhang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/22 19:18
 */
@RestController
@RequestMapping("/hello")
public class ConfigClientHandler {
    @Value("${server.port}")
    private String port;

    @Value("${test}")
    private String test;

    @GetMapping("/index")
    public String index(){
        return this.test + "-" + this.port;
    }
}
