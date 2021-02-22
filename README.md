# ispringcloud
#### Spring Cloud 构建微服务的基本组件

![image-20210220105452228](/Users/xuezhang/Library/Application Support/typora-user-images/image-20210220105452228.png)



Spring Cloud 的服务治理使用 Eureka 来实现，Eureka 是Netflix 开源的REST的服务治理解决方案，Spring Cloud 集成了Eureka，提供服务注册和服务发现的功能，可以和基于Spring boot 搭建的微服务应用轻松完成整合，开箱即用，Spring Cloud Eureka。

#### Spring Cloud Eureka

- Eureka Server 注册中心
- Eureka Client 所有要进行注册的微服务通过Eureka Client 连接到Eureka Server , 完成注册

#### 注册中心 代码实现

- 创建父工程 pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.xuezhang</groupId>
    <artifactId>ispringcloud</artifactId>
    <version>1.0-SNAPSHOT</version>

    <parent>
        <artifactId>spring-boot-starter-parent</artifactId>
        <groupId>org.springframework.boot</groupId>
        <version>2.0.5.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Finchley.SR1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```



- 创建Eureka Server 工程 pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>ispringcloud</artifactId>
        <groupId>com.xuezhang</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>eurekaserver</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
            <version>2.2.3.RELEASE</version>
        </dependency>
    </dependencies>

</project>
```

- 创建配置文件 application.yml，添加Eureka Server 相关配置

```yaml
spring:
  application:
    name: eureka-server #指定服务名称
server:
  port: 8761
eureka:
  instance:
    hostname: localhost #指定主机地址
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

> 属性说明：

`server.port`: 当前 Eureka Server 的服务端口

`eureka.client.register-with-eureka`: 是否将当前的Eureka Server 服务作为客户端进行注册

`eureka.client.fetch-registry`: 是否获取其他Eureka Server 服务的数据

`eureka.clent.service-url.defaultZone`: 注册中心的访问地址

- 创建启动类

```java
package com.xuezhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/20 18:39
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

> 注解说明：

`@SpringBootApplication`：声明该类是Spring boot 服务入口

`@EnableEurekaServer`：声明该类是一个Eureka Server 微服务，提供服务注册和服务发现功能，即注册中心

#### Eureka Client 代码实现

- 创建Module ， pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>ispringcloud</artifactId>
        <groupId>com.xuezhang</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>eureka-client</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            <version>2.0.3.RELEASE</version>
        </dependency>
    </dependencies>
</project>
```

- 创建配置文件 application.yml

```yaml
server:
  port: 8101
spring:
  application:
    name: eureka-client
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true #以IP地址注册到服务中心，相互注册使用IP地址
```

------



#### RestTemplate 的使用

- 什么是RestTemplate

RestTemplate 是Spring 框架提供基于Rest的服务组件，底层对HTTP请求及响应做了封装，提供了很多访问REST服务的方法，可以简化代码开发。

- 如何使用RestTemplate

1. 创建Module， pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>ispringcloud</artifactId>
        <groupId>com.xuezhang</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>restTemplate</artifactId>


</project>
```

2. 创建启动类

```java
package com.xuezhang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/21 12:26
 */
@SpringBootApplication
@Slf4j
public class RestTemplateApplication {
    @Value("${server.port}")
    private String port;

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
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
```

3. 创建实体类

```java
package com.xuezhang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/20 20:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private long id;
    private String name;
    private int age;
}
```

4. 创建控制器

```java
package com.xuezhang.controller;

import com.xuezhang.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/21 12:29
 */
@RestController
@RequestMapping("/rest")
public class RestTemplateHandler {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("findAll")
    public Collection<Student> findAll(){
        return restTemplate.getForEntity("http://192.168.0.198:8101/student/findAll", Collection.class).getBody();
    }

    @GetMapping("/findAll2")
    public Collection<Student> findAll2(){
        return restTemplate.getForObject("http://192.168.0.198:8101/student/findAll", Collection.class);
    }

    @GetMapping("/findById/{id}")
    public Student findById(@PathVariable("id") long id){
        return restTemplate.getForEntity("http://192.168.0.198:8101/student/findById/{id}", Student.class, id).getBody();
    }

    @GetMapping("/findById2/{id}")
    public Student findById2(@PathVariable("id") long id){
        return restTemplate.getForObject("http://192.168.0.198:8101/student/findById/{id}", Student.class, id);
    }

    @PostMapping("/save")
    public void save(@RequestBody Student student){
        restTemplate.postForEntity("http://192.168.0.198:8101/student/save", student, null).getBody();
    }

    @PostMapping("/save2")
    public void save2(@RequestBody Student student){
        restTemplate.postForObject("http://192.168.0.198:8101/student/save", student, null);
    }

    @PutMapping("/update")
    public void update(@RequestBody Student student){
        restTemplate.put("http://192.168.0.198:8101/student/update", student);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") long id){
        restTemplate.delete("http://192.168.0.198:8101/student/delete/{id}", id);
    }
}
```

5. 启动项目测试 RestTemplate 接口

------

#### 服务消费者 consumer

- 创建Module， pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>ispringcloud</artifactId>
        <groupId>com.xuezhang</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>consumer</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            <version>2.0.3.RELEASE</version>
        </dependency>
    </dependencies>
</project>
```

- 创建启动类

  ```java
  package com.xuezhang;
  
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.boot.ApplicationArguments;
  import org.springframework.boot.ApplicationRunner;
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.context.annotation.Bean;
  
  import java.net.InetAddress;
  import java.net.UnknownHostException;
  
  /**
   * @description:
   * @author: 学长
   * @date: 2021/2/21 15:21
   */
  @SpringBootApplication
  @Slf4j
  public class ConsumerApplication {
      @Value("${server.port}")
      private String port;
  
      public static void main(String[] args) {
          SpringApplication.run(ConsumerApplication.class, args);
      }
  
      @Bean
      public ApplicationRunner applicationRunner(){
          return ApplicationArguments -> {
              try{
                  log.info("启动成功：" + "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port);
              }catch (UnknownHostException e){
                  log.error(e.getMessage());
              }
          };
      }
  }
  ```

- 注入RsetTemplate

```java
package com.xuezhang.config;

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
```

- 创建Student 实体类

```java
package com.xuezhang.entitiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/20 20:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private long id;
    private String name;
    private int age;
}
```

- 创建控制器

```java
package com.xuezhang.controller;

import com.xuezhang.entitiy.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/21 15:23
 */
@RestController
@RequestMapping("consumer")
public class ConsumerHandler {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("findAll")
    public Collection<Student> findAll(){
        return restTemplate.getForEntity("http://192.168.0.198:8101/student/findAll", Collection.class).getBody();
    }

    @GetMapping("/findAll2")
    public Collection<Student> findAll2(){
        return restTemplate.getForObject("http://192.168.0.198:8101/student/findAll", Collection.class);
    }

    @GetMapping("/findById/{id}")
    public Student findById(@PathVariable("id") long id){
        return restTemplate.getForEntity("http://192.168.0.198:8101/student/findById/{id}", Student.class, id).getBody();
    }

    @GetMapping("/findById2/{id}")
    public Student findById2(@PathVariable("id") long id){
        return restTemplate.getForObject("http://192.168.0.198:8101/student/findById/{id}", Student.class, id);
    }

    @PostMapping("/save")
    public void save(@RequestBody Student student){
        restTemplate.postForEntity("http://192.168.0.198:8101/student/save", student, null).getBody();
    }

    @PostMapping("/save2")
    public void save2(@RequestBody Student student){
        restTemplate.postForObject("http://192.168.0.198:8101/student/save", student, null);
    }

    @PutMapping("/update")
    public void update(@RequestBody Student student){
        restTemplate.put("http://192.168.0.198:8101/student/update", student);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") long id){
        restTemplate.delete("http://192.168.0.198:8101/student/delete/{id}", id);
    }
}	
```

------

#### 服务网关

Spring Cloud 集成了Zulu 组件，实现服务网关

- 什么是 Zuul

Zuul 是Netflix 提供的一个开源的API网关服务器，是客户端和服务端所有请求的中间层，对外开放一个API，将所有的请求导入统一的入口，屏蔽服务端的具体实现逻辑，Zuul 可以实现反向代理的功能，在网关内部实现动态路由，身份验证，IP过滤，数据监控等。

- 创建Module，pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <parent>
    <artifactId>ispringcloud</artifactId>
    <groupId>com.xuezhang</groupId>
    <version>1.0-SNAPSHOT</version>
  </parent>
  <modelVersion>4.0.0</modelVersion>

  <artifactId>zuul</artifactId>

  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
      <version>2.0.3.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
      <version>2.0.3.RELEASE</version>
    </dependency>
  </dependencies>
</project>
```

- 创建配置文件 application.yml

```yaml
server:
  port: 8104
spring:
  application:
    name: gateway

eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:8761/eureka/

info:
  app.name: gateway
  build.artifactId: gateway
  build.version: 0.0.1-SNAPSHOT

zuul:
  routes:
    eureka-client: /e/**
```

> 属性说明

`zuul.routes.eureka-client`：给服务提供者 eureka-client 设置映射 （eureka-client 表示工程的名称）

- 创建启动类

```java
package com.xuezhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/21 17:10
 */
@EnableZuulProxy
@EnableAutoConfiguration
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
```

> 注解说明

`@EnableZuulProxy`：包含 `@EnbaleZuulServer` ，设置该类是网关的启动类。

`@EnableAutoConfiguration`：可以帮助Spring Boot 应用将所有符合条件的`@Configuration` 配置加载到当前Spring Boot 创建并使用的IoC容器中。

- Zuul 自带负载均衡功能。

------

#### Ribbon 负载均衡

- 什么是Ribbon

Spring Cloud Ribbon 是一个负载均衡的解决方案，Ribbon 是Netflix 发布的负载均衡器，Spring Cloud Ribbon 是基于Netflix Ribbon 实现的，是一个用于对HTPP 控制的负载均衡客户端。

在注册中心对Ribbon 进行注册之后，Ribbon就可以基于某种负载均衡算法，如轮询，随机，加权轮询，加权随机等自动帮助服务消费者调用接口，开发者也可以根据需求自定义负载均衡算法。实际开发中，Spring Cloud Ribbon 需要结合Spring Cloud Eureka 使用，Eureka Server 提供所有可以调用的服务提供者列表，Ribbon 基于特定的负载均衡算法从这些服务提供者中选择要调用的实例。

![image-20210221220856662](/Users/xuezhang/Library/Application Support/typora-user-images/image-20210221220856662.png)



- 创建Module，pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <parent>
    <artifactId>ispringcloud</artifactId>
    <groupId>com.xuezhang</groupId>
    <version>1.0-SNAPSHOT</version>
  </parent>
  <modelVersion>4.0.0</modelVersion>

  <artifactId>ribbon</artifactId>

  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
      <version>2.0.3.RELEASE</version>
    </dependency>
  </dependencies>
</project>
```

- 创建配置文件，application.yml

```yaml
server:
  port: 8105
spring:
  application:
    name: ribbon

eureka:
  client:
    service-url: http://127.0.0.1:8671/eureka/
  instance:
    prefer-ip-address: true
```

- 创建启动类

```java
package com.xuezhang;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/21 22:33
 */
@Slf4j
@SpringBootApplication
public class RibbonApplication {
    public static void main(String[] args) {
        SpringApplication.run(RibbonApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        log.info("注入restTemplate成功！");
        return new RestTemplate();
    }

    @Bean
    public IRule rule(){
        log.info("设置负载均衡算法成功！");
        return new RoundRobinRule();
    }
}

```

> 注解说明

`@LoadBalanced`：声明一个基于 Ribbon 的负载均衡。

- 创建控制器

```java
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
```

- [测试]: http://192.168.0.198:8105/ribbon/index

------



#### Feign

- 什么是Feign

与Ribbon 一样，Feign 也是Netflix 提供的，Feign 是一个声明式的，模版化的Web Service 客户端，它简化了开发者编写Web 服务客户端的操作，开发者可以通过简单的接口和注解调用HTTP API 。Spring Cloud Feign 整合了Ribbon 和 Hystrix，具有可插拔，基于注解，负载均衡，服务熔断等一系列便捷功能。

相比较于Ribbon 和 RestTemplate 的方式，Feign 大大简化代码的开放，Feign 支持多种注解，包括Feign 注解，JAX-RS 注解，Spring MVC 注解等，Spring Cloud 对Feign 进行了优化，整合了Ribbon 和 Eureka，从而使Feign 的使用更加方便。

- Ribbon 和 Feign 的区别

Ribbon 是一个通用的http客户端工具，Feign 是基于 Ribbon 的。

- Feign 的特点

1. Feign 是一个声明式的Web Service 客户端。
2. 支持Feign 注解，Spring MVC 注解，JAX-RS 注解。
3. Feign 基于Ribbon ，使用更加简单。
4. Feign 集成了Hystrix，具备服务熔断的功能。



- 创建Module ，pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>ispringcloud</artifactId>
        <groupId>com.xuezhang</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>feign</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            <version>2.0.3.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
            <version>2.0.3.RELEASE</version>
        </dependency>
    </dependencies>
</project>
```

- 创建配置文件 application.yml

```yaml
server:
  port: 8106
spring:
  application:
    name: feign
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true
```

- 创建启动类

```java
package com.xuezhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/21 23:49
 */
@SpringBootApplication
@EnableFeignClients
public class FeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }
}
```

- 创建Feign 接口

```java
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
```

- 创建Handler

```java
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
```

- [测试]: http://192.168.0.198:8106/feign/index



- 开启服务熔断，在feigin 的 application.yml 中添加开启熔断机制。

```xml
#开启熔断服务
feign:
  hystrix:
    enabled: true
```

- 创建 FeignEurekaClient 接口的实现类 FeignEurekaClientError， 来定义容错处理逻辑，通过注解`@Component` 将该实现类的实例注入IoC容器中。

```java
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
```

- 在FeignEurekaClient 接口定义处通过`@FeignClient` 的 fallback 属性设置映射。

```java
package com.xuezhang.feign;

import com.xuezhang.entity.Student;
import com.xuezhang.feign.Impl.FeignEurekaClientError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/21 23:53
 */
@FeignClient(value = "eureka-client", fallback = FeignEurekaClientError.class)
public interface FeignEurekaClient {
    @GetMapping("/student/findAll")
    Collection<Student> findAll();

    @GetMapping("student/index")
    String index();
}
```

- 启动eureka server 和 feign 测试

------



#### Hystrix 容错机制

- 什么是容错机制

在不改变各个微服务调用关系的前提下，针对错误情况进行预先处理。

- 设计原则
  1. 服务隔离机制
  2. 服务降级机制
  3. 熔断机制
  4. 提供实时的监控和报警功能
  5. 提供实时的配置修改功能

Hystrix 数据监控需要结合 Spring Boot Actuator 来使用，Actuator 提供了对服务的健康监控、数据统计，可以通过hystrix.stream 节点获取监控的请求数据，提供可视化的监控界面。

- 创建Module，pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <parent>
    <artifactId>ispringcloud</artifactId>
    <groupId>com.xuezhang</groupId>
    <version>1.0-SNAPSHOT</version>
  </parent>
  <modelVersion>4.0.0</modelVersion>

  <artifactId>hystrix</artifactId>

  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
      <version>2.0.3.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-openfeign</artifactId>
      <version>2.0.3.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
      <version>2.0.3.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
      <version>2.0.3.RELEASE</version>
    </dependency>
  </dependencies>
</project>
```

- 创建配置文件，application.yml

```xml
server:
  port: 8107
spring:
  application:
    name: hystrix

eureka:
  client:
    service-url:
      dafaultZone: http://localhost:86761/eureka/
  instance:
    prefer-ip-address: true

feign:
  hystrix:
    enabled: true

management:
  endpoints:
    web:
      exposure:
        include: 'hystrix.stream'
```

- 创建启动类

```java
package com.xuezhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/22 13:12
 */
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrixDashboard
public class HystrixApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class, args);
    }
}
```

> 注解说明

`@EnbaleCircuitBreaker`：声明使用数据监控

`@EnableHystrixDashboard`： 声明使用可视化的数据监控

- 创建FeignEurekaClient 接口

```java
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
```

- 创建控制器

```java
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
 * @date: 2021/2/22 13:32
 */
@RestController
@RequestMapping("/hystrix")
public class HystrixHandler {
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
```

- 启动成功后，访问 `http://192.168.16.204:8107/actuator/hystrix.stream` 可以监控到请求的数据。
- 访问`http://192.168.16.204:8107/hystrix` 可以看到可视化的监控界面，输入要监控的地址节点，即可看到该节点的监控数据。

------

#### Spring Cloud 配置中心

Spring Cloud Config，通过服务端可以为多个客户端提供配置服务。Spring Cloud Config 可以将配置文件存储在本地，也可以将配置文件存储在远程Git 仓库，创建 Config Server ，通过它管理所有的配置文件。

##### 本地文件系统

- 创建Module， pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>ispringcloud</artifactId>
        <groupId>com.xuezhang</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>native-config-server</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
            <version>2.1.3.RELEASE</version>
        </dependency>
    </dependencies>
</project>
```

- 创建配置文件application.yml

```yaml
server:
  port: 8762
spring:
  application:
    name: native-config-server
  profiles:
    active: native
  cloud:
    config:
      server:
        native:
          search-locations: classpath:/shared
```

> 配置说明

`spring.profiles.active`： 配置文件的获取方式

`spring.cloud.connfig.server.native.search-locations`：本地配置文件存放的路径

- resources 路径下创建shared 文件夹，并在此路径下创建 configclient-dev.yml。

```yaml
server:
  port: 8108
foo: foo version 1
```

- 创建启动类

```java
package com.xuezhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/22 14:37
 */
@SpringBootApplication
@EnableConfigServer
public class NativeConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NativeConfigServerApplication.class, args);
    }
}
```

> 注解说明

`@EnableConfigServer`：声明配置中心

#### 创建客户端读取本地配置中心的配置文件

- 创建Module，pom.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <parent>
          <artifactId>ispringcloud</artifactId>
          <groupId>com.xuezhang</groupId>
          <version>1.0-SNAPSHOT</version>
      </parent>
      <modelVersion>4.0.0</modelVersion>
  
      <artifactId>native-config-client</artifactId>
  
      <dependencies>
          <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-config</artifactId>
              <version>2.1.3.RELEASE</version>
          </dependency>
      </dependencies>
  </project>
  ```

- 创建配置文件bootstrap.yml，读取本地配置中心的相关信息。

```yaml
spring:
  application:
    name: configclient
  profiles:
    active: dev
  cloud:
    config:
      uri: http://localhost:8762
      fail-fast: true
```

> 配置说明

`spring.cloud.config.uri`：本地Config Server 的访问地址

`spring.cloud.config.fail-fast`：设置客户端优先判断Config Server 获取是否正常。

通过`spring.application.name` 结合 `spring.profiles.active` 拼接目标配置文件名 configclient-dev.yml，去Config Server 中查找该文件。

- 创建客户端启动类

```java
package com.xuezhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/22 15:13
 */
@SpringBootApplication
public class NativeConfigClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(NativeConfigClientApplication.class, args);
    }
}
```

- 创建控制器

```java
package com.xuezhang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/22 15:14
 */
@RestController
@RequestMapping("/native")
public class NativeConfigHandler {
    @Value("${server.port}")
    private String port;

    @Value("${foo}")
    private String foo;

    @GetMapping("/index")
    public String index(){
        return this.port + "-" + this.foo;
    }
}
```

- 测试 `http://localhost:8108/native/index`

#### Spring Cloud Config 远程配置

