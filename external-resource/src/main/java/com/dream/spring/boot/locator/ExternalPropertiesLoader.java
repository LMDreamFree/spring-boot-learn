package com.dream.spring.boot.locator;

import com.dream.spring.boot.source.ExternalProperties;
import com.dream.spring.boot.utils.PropertiesSourceHelper;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author lim
 * @date 2020/7/25 17:16
 * @className ExternalPropertiesLoader
 * @end spring cloud 2020.0.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class ExternalPropertiesLoader implements PropertySourceLocator {



    private final ExternalProperties externalProperties;

    public ExternalPropertiesLoader(ExternalProperties externalProperties) {
        this.externalProperties = externalProperties;
    }

    @Override
    public PropertySource<?> locate(Environment environment) {
        CompositePropertySource composite = new CompositePropertySource(
                "EXTERNAL_SOURCE");
        externalProperties.setEnvironment(environment);
        String fileName = environment.getProperty("source.fileName");
        PropertySource<?> properties = PropertiesSourceHelper.loadProperties(fileName);
        composite.addFirstPropertySource(properties);
        return composite;
    }
}
