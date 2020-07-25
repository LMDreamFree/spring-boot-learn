package com.dream.spring.boot.destruction;

import com.dream.spring.boot.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import static com.dream.spring.boot.configuration.BeanPostProcessorConfiguration.DESTRUCT;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lim
 * @date 2020/7/25 14:49
 * @className MyDestructionBeanPostProcessor
 */
@Configuration(proxyBeanMethods = false)
public class MyDestructionBeanPostProcessor implements DestructionAwareBeanPostProcessor {
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals(beanName, DESTRUCT) && User.class.equals(bean.getClass())) {
            System.err.println("销毁之前 ::: com.dream.spring.boot.destruction.MyDestructionBeanPostProcessor.postProcessBeforeDestruction");
        }
    }

    @Override
    public boolean requiresDestruction(Object bean) {
        return true;
    }
}
