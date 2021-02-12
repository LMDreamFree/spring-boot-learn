package com.dream.spring.aop.features;

import com.dream.spring.aop.features.aspect.AspectJConfiguration;
import com.dream.spring.aop.service.EchoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lim
 * @date 2021/2/11 0:31
 * @className AspectJPointCutSample
 * @desc xml PointCut示例
 */
public class AspectJSchemaBasedPointCutSample {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/spring-aop-context.xml");
        context.refresh();

        EchoService echoService = context.getBean("echoService",EchoService.class);
        System.out.println(echoService.hello("xml aspect"));;

        context.close();
    }
}
