package com.xuezhang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/22 23:25
 */
@RestController
@RequestMapping("/zipkin")
public class ZipkinClientHandler {
    @Value("${server.port}")
    private String port;

    @GetMapping("/index")
    public String index(){
        return port;
    }
}
