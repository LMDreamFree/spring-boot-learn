package com.dream.spring.boot.spring.boot;

import com.dream.spring.boot.spring.boot.annotation.Injection;
import com.dream.spring.boot.spring.boot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME;

/**
 * @author: LMDreamFree
 * @desc: TODO
 * @date: 2020/7/25 11:44
 */
@ComponentScan(basePackages = "com.dream.spring.boot")
public class AutowiredInjectApplication {

    @Autowired
    private User user;

    @Injection
    private String string;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AutowiredInjectApplication.class);
        context.refresh();
        int count = context.getBeanDefinitionCount();
        System.out.printf("已注册bean的数量 %d %n",count);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        Stream.of(beanDefinitionNames).forEach(System.err::println);
        AutowiredInjectApplication contextBean = context.getBean(AutowiredInjectApplication.class);
        /**依赖注入*/
        System.out.println("User bean :::: " + contextBean.user);
        System.out.println("String bean :::: " + contextBean.string);
        context.close();
    }
    /**
     *期待输出 null null
     */
    /*@Bean(name = AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
    public AutowiredAnnotationBeanPostProcessor beanPostProcessor(){
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        Set<Class<? extends Annotation>> annotations = new HashSet<>(Arrays.asList(Injection.class,Autowired.class));
        beanPostProcessor.setAutowiredAnnotationTypes(annotations);
        return beanPostProcessor;
    }*/
    /**期待输出 string **/
    @Bean(name = AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor(){
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        Set<Class<? extends Annotation>> annotations = new HashSet<>();
        annotations.add(Injection.class);
        beanPostProcessor.setAutowiredAnnotationTypes(annotations);
        return beanPostProcessor;
    }
    /**
     * 期待输出 string user
     * static 提升bean注册优先级
     * {@link AnnotationConfigUtils#registerAnnotationConfigProcessors(org.springframework.beans.factory.support.BeanDefinitionRegistry, java.lang.Object)}
     * 此方法会判断是否存在 <code>{@link AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME} </code>bean
     * 如果存在则不创建 即 Autowired 注解无法生效
     */
   /* @Bean(name = "AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME")
    public static AutowiredAnnotationBeanPostProcessor processor() {
        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
        processor.setAutowiredAnnotationType(Injection.class);
        return processor;
    }*/
}
