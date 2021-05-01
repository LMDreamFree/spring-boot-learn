package com.dream.spring.boot.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * {@link Endpoint}
 * </p>
 *
 * @author lim
 * @date 2021/5/1 22:51
 * @className ArchitectureEndpointAutoConfiguration
 */
@Configuration
public class ArchitectureEndpointAutoConfiguration {



    @Bean
    public ArchitectureEndpoint architectureEndpoint(){
        return new ArchitectureEndpoint();
    }
}
