package com.dream.stream.http.config;

import com.dream.stream.http.HttpMessageChannelBinder;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lim
 * @date 2021/11/4 21:13
 * @className HttpMessageChannelBinderAutoConfiguration
 */
@Configuration
@ConditionalOnMissingBean(Binder.class)
public class HttpMessageChannelBinderAutoConfiguration {

    @Bean
    public HttpMessageChannelBinder httpMessageChannelBinder(final OkHttpClient okHttpClient) {
        return new HttpMessageChannelBinder(okHttpClient);
    }
}
