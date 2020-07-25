package com.dream.spring.boot.bean;

import com.dream.spring.boot.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lim
 * @date 2020/7/25 11:41
 * @className UserConfiguration
 */
@Configuration
public class UserConfiguration {

    @Bean
    public User user() {
        User user = new User();
        user.setId(1);
        user.setName("叫我墨罹丶");
        return user;
    }

    @Bean
    public String string(){
        return "Hello AutowiredAnnotationBeanPostProcessor";
    }
}
