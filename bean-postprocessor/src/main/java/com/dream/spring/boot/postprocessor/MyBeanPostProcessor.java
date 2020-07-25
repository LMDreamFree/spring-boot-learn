package com.dream.spring.boot.postprocessor;

import com.dream.spring.boot.configuration.BeanPostProcessorConfiguration;
import com.dream.spring.boot.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lim
 * @date 2020/7/25 14:45
 * @className MyPostProcessor
 */
@Configuration(proxyBeanMethods = false)
public class MyBeanPostProcessor implements BeanPostProcessor {
    /**
     * @desc: 初始化之前
     * @date: 2020/7/25 15:24
     * @return  返回null 不做修改
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals(beanName, BeanPostProcessorConfiguration.AFTER_INIT) && User.class.equals(bean.getClass())){
            System.err.println("初始化之前 :::: com.dream.spring.boot.postprocessor.MyBeanPostProcessor.postProcessBeforeInitialization");
            User user = User.class.cast(bean);
            user.setSource("com.dream.spring.boot.postprocessor.MyBeanPostProcessor.postProcessBeforeInitialization");
            user.setVersion(4);
            return user;
        }
        return null;
    }
    /**
     * @desc: 初始化之后
     * @date: 2020/7/25 15:24
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals(beanName, BeanPostProcessorConfiguration.AFTER_INIT) && User.class.equals(bean.getClass())){
            System.err.println("初始化之后 :::: com.dream.spring.boot.postprocessor.MyBeanPostProcessor.postProcessAfterInitialization");
            User user = User.class.cast(bean);
            user.setSource("com.dream.spring.boot.postprocessor.MyBeanPostProcessor.postProcessAfterInitialization");
            user.setVersion(5);
            return user;
        }
        return null;
    }
}
