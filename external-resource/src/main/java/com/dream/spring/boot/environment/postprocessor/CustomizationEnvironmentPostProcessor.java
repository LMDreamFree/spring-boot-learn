package com.dream.spring.boot.environment.postprocessor;

import com.dream.spring.boot.utils.PropertiesSourceHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;


/**
 * @author lim
 * @date 2021/2/14 12:05
 * @className CustomizationEnvironmentPostProcessor
 * @desc 自定义EnvironmentPostProcessor
 * @begin spring cloud 2020.0.0
 */
public class CustomizationEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        String fileName = environment.getProperty("source.name");
        PropertySource<?> properties = PropertiesSourceHelper.loadProperties(fileName);
        propertySources.addFirst(properties);
    }
}
