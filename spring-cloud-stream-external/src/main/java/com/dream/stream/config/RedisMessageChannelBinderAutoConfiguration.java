package com.dream.stream.config;

import com.dream.stream.redis.RedisMessageChannelBinder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author lim
 * @date 2021/11/4 21:13
 * @className HttpMessageChannelBinderAutoConfiguration
 */
@Configuration
@ConditionalOnMissingBean(Binder.class)
public class RedisMessageChannelBinderAutoConfiguration {

    @Bean
    public RedisMessageChannelBinder httpMessageChannelBinder(final RedisTemplate redisTemplate) {
        return new RedisMessageChannelBinder(redisTemplate);
    }
}
