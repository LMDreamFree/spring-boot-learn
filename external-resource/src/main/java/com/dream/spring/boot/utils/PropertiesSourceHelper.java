package com.dream.spring.boot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p>
 * 加载资源
 * </p>
 *
 * @author lim
 * @date 2021/2/14 12:15
 * @className PropertiesSourceHelper
 * @desc PropertiesSourceHelper
 */
public abstract class PropertiesSourceHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesSourceHelper.class);

    private static final PropertySourceFactory DEFAULT_PROPERTY_SOURCE_FACTORY = new DefaultPropertySourceFactory();

    private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

    /**
     * @author: lim
     * @desc: 根据配置文件加载配置文件类型 并返回 PropertySource
     * @date: 2021/2/14 12:20
     */
    public static PropertySource<?> loadProperties(String sourceName) {

        Resource resource = RESOURCE_LOADER.getResource(sourceName);

        // 会有乱码出现
//        PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();
        try {
            EncodedResource encodedResource = new EncodedResource(resource, StandardCharsets.UTF_8);
//            List<PropertySource<?>> load = loader.load(sourceName, encodedResource.getResource());
            PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
            PropertySource<?> propertySource = DEFAULT_PROPERTY_SOURCE_FACTORY.createPropertySource(sourceName, encodedResource);
            propertiesFactoryBean.setLocation(encodedResource.getResource());
            propertiesFactoryBean.afterPropertiesSet();
            propertiesFactoryBean.getObject();
            return propertySource;
        } catch (IOException e) {
            LOGGER.error("load resource failed.",e.getMessage(),e);
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public static Map<String, Object> propertiesToMap(Properties properties) {
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
