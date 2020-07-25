package com.dream.spring.boot.locator;

import com.dream.spring.boot.source.ExternalProperties;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lim
 * @date 2020/7/25 17:16
 * @className ExternalPropertiesLoader
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
        Properties properties = loadProperty(fileName);
        Map<String, Object> map = propertiesToMap(properties);
        MapPropertySource mapPropertySource = new MapPropertySource("external_map", map);
        composite.addFirstPropertySource(mapPropertySource);
        return composite;
    }

    private Properties loadProperty(String sourceName) {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(sourceName);
        PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();
        try {
            List<PropertySource<?>> load = loader.load(sourceName, resource);
            PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
            EncodedResource encodedResource = new EncodedResource(resource, StandardCharsets.UTF_8);
            propertiesFactoryBean.setLocation(encodedResource.getResource());
            propertiesFactoryBean.afterPropertiesSet();
            return propertiesFactoryBean.getObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, Object> propertiesToMap(Properties properties) {
        Map<String, Object> result = new HashMap<>(16);
        Enumeration<String> keys = (Enumeration<String>) properties.propertyNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            Object value = properties.getProperty(key);
            if (value != null) {
                result.put(key, ((String) value).trim());
            } else {
                result.put(key, null);
            }
        }
        return result;
    }
}
