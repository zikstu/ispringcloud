package com.xuezhang.feign.Impl;

import com.xuezhang.entity.Student;
import com.xuezhang.feign.FeignEurekaClient;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/22 08:59
 */
@Component
public class FeignEurekaClientError implements FeignEurekaClient {
    @Override
    public Collection<Student> findAll() {
        return null;
    }

    @Override
    public String index() {
        return "服务器维护中...";
    }
}
