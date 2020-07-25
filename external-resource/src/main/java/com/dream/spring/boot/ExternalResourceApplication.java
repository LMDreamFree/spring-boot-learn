package com.dream.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author: lim
 * @desc:  外部化配置
 * @date: 2020/7/25 16:23
 */
@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
public class ExternalResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalResourceApplication.class, args);
    }

}
