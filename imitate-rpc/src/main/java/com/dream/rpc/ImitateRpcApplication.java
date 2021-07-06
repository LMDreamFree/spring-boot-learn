package com.dream.rpc;

import com.dream.rpc.annotation.EnableRpcServices;
import com.dream.rpc.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <p>模仿feign实现</p>
 * @author cloud
 * @date: 2021/7/3 22:21
 */
@EnableRpcServices("com.dream.rpc.client")
@EnableDiscoveryClient
@SpringBootApplication
public class ImitateRpcApplication {

    @Autowired
    private UserClient userClient;

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ImitateRpcApplication.class, args);
        UserClient bean = applicationContext.getBean(UserClient.class);
        String hello = bean.send("hello");
        System.out.println(hello);
    }

}
