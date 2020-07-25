package com.dream.spring.boot.spring.boot;

import com.dream.spring.boot.spring.boot.annotation.EnableScanner;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * @desc: 自定义扫描器
 * @date: 2020/7/25 11:13
 */
@EnableScanner
public class ComponentProviderBootstrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ComponentProviderBootstrap.class);
        context.refresh();
        int count = context.getBeanDefinitionCount();
        System.err.printf("已加载的bean的数量::::%d %n", count);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        Stream.of(beanDefinitionNames).forEach(System.err::println);
        BeanDefinition beanDefinition = context.getBeanDefinition("77777");
        System.out.printf("beanDefinition %s %n", beanDefinition);
        context.close();
    }

}
