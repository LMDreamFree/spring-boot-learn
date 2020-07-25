package com.dream.spring.boot.configuration;

import com.dream.spring.boot.health.HealthCheckConfig;
import com.dream.spring.boot.locator.ExternalPropertiesLoader;
import com.dream.spring.boot.source.ExternalProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * <p>
 * {@link ExternalSourceAutoConfiguration}
 * </p>
 *
 * @author lim
 * @date 2020/7/25 17:22
 * @className ExternalSourceAutoConfiguration
 */
@Component
public class ExternalSourceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ExternalProperties externalProperties() {
        return new ExternalProperties();
    }

    @Bean
    public HealthCheckConfig healthCheckConfig(ExternalProperties externalProperties) {
        return new HealthCheckConfig(externalProperties);
    }

    @Bean
    public ExternalPropertiesLoader propertiesLoader(ExternalProperties externalProperties) {
        return new ExternalPropertiesLoader(externalProperties);
    }
}
