package com.dream.spring.boot.spring.boot.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lim
 * @date 2020/7/25 11:09
 * @className ComponentConfiguration
 */
@Configuration
public class ComponentConfiguration {
    @Bean
    public String componentStr() {
        return "Hello ClassPathScanningCandidateComponentProvider";
    }

}
