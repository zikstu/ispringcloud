package com.xuezhang.feign;

import com.xuezhang.entity.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/21 23:53
 */
@FeignClient(value = "eureka-client")
public interface FeignEurekaClient {
    @GetMapping("/student/findAll")
    Collection<Student> findAll();

    @GetMapping("student/index")
    String index();
}
