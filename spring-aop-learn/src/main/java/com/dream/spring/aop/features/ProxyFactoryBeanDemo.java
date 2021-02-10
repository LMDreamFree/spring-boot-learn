package com.dream.spring.aop.features;

import com.dream.spring.aop.service.EchoService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 适用于IoC容器
 * @author lim
 * @date 2021/2/10 23:56
 * @className ProxyFactoryBeanDemo
 * {@link ProxyFactoryBean }
 */
@Aspect
@Configuration
public class ProxyFactoryBeanDemo {
    public static void main(String[] args) {
        String resource = "classpath:/META-INF/spring-aop-context.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(resource);
        EchoService echoService = context.getBean("echoServiceProxyFactoryBean", EchoService.class);

        System.out.println(echoService.hello("Hello World"));
        context.close();
    }
}
