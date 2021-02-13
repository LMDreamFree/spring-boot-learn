package com.dream.spring.aop.features;

import com.dream.spring.aop.service.EchoService;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>
 * spring aop 自动代理
 * {@link BeanNameAutoProxyCreator }
 * </p>
 *
 * @author lim
 * @date 2021/2/13 11:59
 * @className AspectJSchemaBasedAutoProxyDemo
 * @desc TODO
 */
public class AspectJSchemaBasedAutoProxyDemo {
    public static void main(String[] args) {
        String resource = "META-INF/spring-aop-auto-proxy-context.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(resource);
        EchoService echoService = context.getBean(EchoService.class);
        System.out.println(echoService.hello("auto proxy"));;
        context.close();
    }
}
