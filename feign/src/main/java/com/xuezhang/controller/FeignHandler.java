package com.xuezhang.controller;

import com.xuezhang.entity.Student;
import com.xuezhang.feign.FeignEurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/21 23:56
 */
@RestController
@RequestMapping("/feign")
public class FeignHandler {
    @Autowired
    private FeignEurekaClient feignEurekaClient;

    @GetMapping("/findAll")
    public Collection<Student> findAll(){
        return feignEurekaClient.findAll();
    }

    @GetMapping("/index")
    public String index(){
        return feignEurekaClient.index();
    }
}
