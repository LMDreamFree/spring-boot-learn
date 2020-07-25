package com.dream.spring.boot.configuration;

import com.dream.spring.boot.annotation.EnableScanner;
import com.dream.spring.boot.annotation.Scanner;
import com.dream.spring.boot.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lim
 * @date 2020/7/25 10:45
 * @className RegistrarConfiguration
 */
public class RegistrarConfiguration implements ImportBeanDefinitionRegistrar {

    public BeanNameGenerator beanNameGenerator = AnnotationBeanNameGenerator.INSTANCE;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider componentProvider = new ClassPathScanningCandidateComponentProvider(false);
        Map<String, Object> defaultAttrs = importingClassMetadata
                .getAnnotationAttributes(EnableScanner.class.getName(), true);
        String value = "basePackages";
        String basePackage = null;
        String packageName = ClassUtils.getPackageName(importingClassMetadata.getClassName());
        if (defaultAttrs.containsKey(value)) {
            basePackage = String.valueOf(defaultAttrs.get(value));
        }
        packageName = StringUtils.isNotBlank(basePackage) ? basePackage : packageName;
        /**扫描某个注解标注的类*/
        componentProvider.addIncludeFilter(new AnnotationTypeFilter(Scanner.class));
        componentProvider.addIncludeFilter(new AnnotationTypeFilter(Component.class));
        /**扫描某个接口的实现类*/
        componentProvider.addIncludeFilter(new AssignableTypeFilter(IUserService.class));

        Set<BeanDefinition> components = componentProvider.findCandidateComponents(packageName);
        for (BeanDefinition beanDefinition : components) {
            String beanName = beanNameGenerator.generateBeanName(beanDefinition, registry);
            if (!registry.containsBeanDefinition(beanName)) {
                registry.registerBeanDefinition(beanName, beanDefinition);
                /**覆盖最后一个BeanDefinition*/
                registry.registerBeanDefinition("77777", beanDefinition);
            }
        }
    }
}
