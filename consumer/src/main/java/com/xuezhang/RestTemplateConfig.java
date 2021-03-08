package com.xuezhang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @description: 注入 RestTemplate
 * @author: 学长
 * @date: 2021/2/21 15:31
 */
@Configuration
@Slf4j
public class RestTemplateConfig {
    /**
     * 配置 RestTemplate
     * @param clientHttpRequestFactory
     * @return
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory){
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        //解决中文乱码
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        //todo 处理异常 restTemplate.setErrorHandler()

        log.info("RestTemplate注入成功！");

        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(){
        //创建httpClient 简单工厂
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        // 设置链接超时
        factory.setConnectTimeout(5000);

        // 设置读取超时
        factory.setReadTimeout(5000);

        return factory;
    }
}
