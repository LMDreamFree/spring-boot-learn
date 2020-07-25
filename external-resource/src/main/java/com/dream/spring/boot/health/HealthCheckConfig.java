package com.dream.spring.boot.health;

import com.dream.spring.boot.source.ExternalProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lim
 * @date 2020/7/25 17:11
 * @className HealthCheck
 */
public class HealthCheckConfig extends AbstractHealthIndicator {

    private final ExternalProperties externalProperties;

    public HealthCheckConfig(ExternalProperties externalProperties) {
        this.externalProperties = externalProperties;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        String name = externalProperties.getName();
        if (StringUtils.isNotBlank(name)){
            builder.up().withDetail(String.format("name: '%s'",
                    name), "config is health");
        }else {
            builder.down().withDetail(String.format("name: '%s'",
                    name), "config is not ok");
        }

    }
}
