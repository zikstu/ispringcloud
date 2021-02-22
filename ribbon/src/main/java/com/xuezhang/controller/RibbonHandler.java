package com.xuezhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * @description:
 * @author: 学长
 * @date: 2021/2/21 22:39
 */
@RestController
@RequestMapping("ribbon")
public class RibbonHandler {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/index")
    public String index(){
       return restTemplate.getForObject("http://eureka-client/student/index", String.class);
    }
}
