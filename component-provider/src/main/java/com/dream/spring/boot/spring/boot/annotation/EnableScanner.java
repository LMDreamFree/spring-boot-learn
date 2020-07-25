package com.dream.spring.boot.spring.boot.annotation;

import com.dream.spring.boot.spring.boot.configuration.RegistrarConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lim
 * @date 2020/7/25 10:51
 * @className EnableScanner
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RegistrarConfiguration.class)
public @interface EnableScanner {
    String basePackages() default "";
}
