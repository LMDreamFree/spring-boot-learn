package com.dream.spring.boot;

import com.dream.spring.boot.entity.User;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import static com.dream.spring.boot.configuration.BeanPostProcessorConfiguration.*;

/**
 * @author: lim
 * @desc: {@link BeanPostProcessor}
 * @date: 2020/7/25 14:36
 */
@ComponentScan(basePackages = "com.dream.spring.boot")
public class BeanPostProcessorBootstrap {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(BeanPostProcessorBootstrap.class);
        context.refresh();
        User instantBean = context.getBean(INSTANT, User.class);
        User destructBean = context.getBean(DESTRUCT, User.class);
        User afterInitBean = context.getBean(AFTER_INIT, User.class);
        System.out.println(instantBean);
        System.out.println(destructBean);
        System.out.println(afterInitBean);
        context.close();
    }
}
