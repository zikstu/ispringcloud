package com.xuezhang.feign;

import com.xuezhang.entity.Student;
import com.xuezhang.feign.Impl.FeignEurekaClientError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/21 23:53
 */
@Component("feignEurekaClient")
@FeignClient(value = "eureka-client", fallback = FeignEurekaClientError.class, decode404 = true)
public interface FeignEurekaClient {
    @GetMapping("/student/findAll")
    Collection<Student> findAll();

    @GetMapping("student/index")
    String index();
}
