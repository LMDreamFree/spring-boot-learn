package com.dream.spring.boot.locator;

import com.dream.spring.boot.source.ExternalProperties;
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

    public final PropertySourceFactory DEFAULT_PROPERTY_SOURCE_FACTORY = new DefaultPropertySourceFactory();

    public final DefaultResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

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
        PropertySource<?> properties = loadProperties(fileName);
       // MapPropertySource mapPropertySource = new MapPropertySource("external_map", properties);
        composite.addFirstPropertySource(properties);
        return composite;
    }

    private PropertySource<?> loadProperties(String sourceName) {

        Resource resource = RESOURCE_LOADER.getResource(sourceName);

        PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();
        try {
            EncodedResource encodedResource = new EncodedResource(resource, StandardCharsets.UTF_8);
            List<PropertySource<?>> load = loader.load(sourceName, encodedResource.getResource());
            PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
            PropertySource<?> propertySource = DEFAULT_PROPERTY_SOURCE_FACTORY.createPropertySource(sourceName, encodedResource);
            propertiesFactoryBean.setLocation(encodedResource.getResource());
            propertiesFactoryBean.afterPropertiesSet();
            propertiesFactoryBean.getObject();
            return propertySource;
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
