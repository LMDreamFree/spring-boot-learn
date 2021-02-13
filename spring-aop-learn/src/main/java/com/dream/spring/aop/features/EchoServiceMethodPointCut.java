package com.dream.spring.aop.features;

import com.dream.spring.aop.service.EchoService;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author lim
 * @date 2021/2/12 16:03
 * @className EchoServicePointCut
 */
public class EchoServiceMethodPointCut implements Pointcut {

    public static final EchoServiceMethodPointCut INSTANCE = new EchoServiceMethodPointCut();

    private EchoServiceMethodPointCut() {

    }

    @Override
    public ClassFilter getClassFilter() {
        // 凡是EchoService接口或者其子类
        return clazz -> EchoService.class.isAssignableFrom(clazz);
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return new MethodMatcher() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                // 判断hello方法是否匹配到
                return ObjectUtils.nullSafeEquals("hello", method.getName())
                        && method.getParameterTypes().length == 1
                        && Objects.equals(String.class, method.getParameterTypes()[0]);
            }

            @Override
            public boolean isRuntime() {
                return false;
            }

            @Override
            public boolean matches(Method method, Class<?> targetClass, Object... args) {
                return false;
            }
        };
    }
}
