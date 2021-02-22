package com.xuezhang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/20 18:39
 */
@SpringBootApplication
@EnableEurekaServer
@Slf4j
public class EurekaServerApplication {
    @Value("${server.port}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

    /**
     * 启动成功
     * @return
     */
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
