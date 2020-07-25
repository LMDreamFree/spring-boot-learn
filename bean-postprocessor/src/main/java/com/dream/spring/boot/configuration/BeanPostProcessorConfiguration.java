package com.dream.spring.boot.configuration;

import com.dream.spring.boot.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadLocalRandom;


/**
 * <p>
 * TODO
 * </p>
 *
 * @author lim
 * @date 2020/7/25 14:39
 * @className BeanPostProcessorConfiguration
 */
@Configuration
public class BeanPostProcessorConfiguration {

    public static final String INSTANT = "instantUser";
    public static final String AFTER_INIT = "afterInitUser";
    public static final String DESTRUCT = "destructUser";

    @Bean(INSTANT)
    public User instantUser() {
        return createUser("com.dream.spring.boot.configuration.BeanPostProcessorConfiguration.instantUser");
    }

    @Bean(DESTRUCT)
    public User destructUser() {
        return createUser("com.dream.spring.boot.configuration.BeanPostProcessorConfiguration.destructUser");
    }

    @Bean(AFTER_INIT)
    public User afterInitUser() {
        return createUser("com.dream.spring.boot.configuration.BeanPostProcessorConfiguration.afterInitUser");
    }

    private User createUser(String source) {
        User user = new User();
        user.setId(ThreadLocalRandom.current().nextInt());
        user.setName("叫我墨罹丶");
        user.setSource(source);
        user.setVersion(1);
        return user;
    }
}
