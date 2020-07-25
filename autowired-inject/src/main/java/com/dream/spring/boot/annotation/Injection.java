package com.dream.spring.boot.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 自定义依赖注入注解
 * </p>
 *
 * @author lim
 * @date 2020/7/25 11:39
 * @className Injection
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Injection {
    /**
     * Declares whether the annotated dependency is required.
     * <p>Defaults to {@code true}.
     */
    boolean required() default true;
}
