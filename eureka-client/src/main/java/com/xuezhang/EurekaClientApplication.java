package com.xuezhang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/20 20:00
 */
@Slf4j
@SpringBootApplication
public class EurekaClientApplication {
    @Value("${server.port}")
    private String port;

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(){
        return ApplicationArguments -> {
            try {
                log.info("启动成功：" + "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port);
            }catch (UnknownHostException e){
                log.error(e.getMessage());
            }
        };
    }
}
