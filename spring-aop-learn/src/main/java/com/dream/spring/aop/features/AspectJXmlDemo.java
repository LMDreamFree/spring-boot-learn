package com.dream.spring.aop.features;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lim
 * @date 2021/2/10 23:19
 * @className AspectJDemo
 * xml  AOP示例
 */
@Aspect  // 声明为Aspect切面
@Configuration // Configuration class
public class AspectJXmlDemo {
    public static void main(String[] args) {
        String resource = "classpath:/META-INF/spring-aop-context.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(resource);
 //       AspectJXmlDemo bean = context.getBean(AspectJXmlDemo.class);
        context.close();
    }
}
