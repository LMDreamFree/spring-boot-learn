package com.dream.spring.aop.features;

import com.dream.spring.aop.features.aspect.AspectJConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author lim
 * @date 2021/2/11 0:31
 * @className AspectJPointCutSample
 * @desc TODO
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectJPointCutSample {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AspectJPointCutSample.class, AspectJConfiguration.class);
        context.refresh();

        AspectJPointCutSample sample = context.getBean(AspectJPointCutSample.class);
        sample.execute();

        context.close();
    }

    public void execute() {
        System.out.println("execute()....");
    }
}
