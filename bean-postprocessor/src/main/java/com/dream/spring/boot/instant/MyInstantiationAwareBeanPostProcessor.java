package com.dream.spring.boot.instant;

import com.dream.spring.boot.configuration.BeanPostProcessorConfiguration;
import com.dream.spring.boot.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.ThreadLocalRandom;

import static com.dream.spring.boot.configuration.BeanPostProcessorConfiguration.*;

/**
 * <p>
 * {@link InstantiationAwareBeanPostProcessor} 实例化之前操作
 * </p>
 *
 * @author lim
 * @date 2020/7/25 14:44
 * @className MyInstantiationAwareBeanPostProcessor
 */
@Configuration(proxyBeanMethods = false)
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    /**
     * @desc: 初始化之前
     * @date: 2020/7/25 14:59
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals(beanName, INSTANT) && User.class.equals(beanClass)) {
            System.err.println("实例化之前 com.dream.spring.boot.instant.MyInstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation");
            User user = new User();
            user.setId(ThreadLocalRandom.current().nextInt());
            user.setVersion(2);
            user.setSource("com.dream.spring.boot.instant.MyInstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation");
            user.setName("org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation");
            return user;
        }
        return null;
    }

    /**
     * @return 判断bean是否要进行 populate
     * @desc: 初始化之后
     * @date: 2020/7/25 14:59
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals(beanName, AFTER_INIT) && User.class.equals(bean.getClass())) {
            System.err.println("实例化之后 com.dream.spring.boot.instant.MyInstantiationAwareBeanPostProcessor.postProcessAfterInstantiation");
            User user = new User();
            user.setId(ThreadLocalRandom.current().nextInt());
            user.setVersion(3);
            user.setSource("com.dream.spring.boot.instant.MyInstantiationAwareBeanPostProcessor.postProcessAfterInstantiation");
            user.setName("org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation");
            return false;
        }
        return true;
    }

    /**
     * @return 返回NULL证明不修改值  用构造注入时无法读取
     * @desc: 对属性值进行操作
     * @date: 2020/7/25 14:59
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals(beanName, DESTRUCT) && User.class.equals(bean.getClass())) {
            System.err.println(beanName + " 实例化 修改属性值 com.dream.spring.boot.instant.MyInstantiationAwareBeanPostProcessor.postProcessProperties");
            final MutablePropertyValues propertyValues;
            if (pvs instanceof MutablePropertyValues) {
                propertyValues = (MutablePropertyValues) pvs;
            } else {
                propertyValues = new MutablePropertyValues();
            }
            if (propertyValues.contains("source")) {
                propertyValues.removePropertyValue("source");
                propertyValues.add("source", "com.dream.spring.boot.instant.MyInstantiationAwareBeanPostProcessor.postProcessProperties");
            }
            Object version = propertyValues.get("version");
            System.out.println("com.dream.spring.boot.instant.MyInstantiationAwareBeanPostProcessor.postProcessProperties 修改之前的值" + version);
            propertyValues.removePropertyValue("version");
            propertyValues.add("version", 3);
            return pvs;
        }
        return null;
    }
}
