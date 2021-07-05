package com.dream.rpc.annotation;

import com.dream.rpc.config.EnableRpcRegistrar;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * {@link EnableFeignClients}
 * <p>TODO</p>
 * @author cloud
 * @date: 2021/7/3 22:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(EnableRpcRegistrar.class)
public @interface EnableRpcServices {
    /**
     * Alias for the {@link #basePackages()} attribute. Allows for more concise annotation
     * declarations e.g.: {@code @ComponentScan("org.my.pkg")} instead of
     * {@code @ComponentScan(basePackages="org.my.pkg")}.
     * @return the array of 'basePackages'.
     */
    String[] value() default {};
}
