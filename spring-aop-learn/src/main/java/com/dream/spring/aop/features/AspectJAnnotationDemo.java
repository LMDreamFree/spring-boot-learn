package com.dream.spring.aop.features;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author lim
 * @date 2021/2/10 23:19
 * @className AspectJDemo
 * @desc TODO
 */
@Aspect  // 声明为Aspect切面
@Configuration // Configuration class
@EnableAspectJAutoProxy // 激活Aspect
public class AspectJAnnotationDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AspectJAnnotationDemo.class);
        context.refresh();
        AspectJAnnotationDemo bean = context.getBean(AspectJAnnotationDemo.class);
        System.out.println(bean);
        context.close();
    }
}
