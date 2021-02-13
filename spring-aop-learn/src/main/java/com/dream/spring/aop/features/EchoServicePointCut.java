package com.dream.spring.aop.features;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * @author lim
 * @date 2021/2/12 16:03
 * @className EchoServicePointCut
 */
public class EchoServicePointCut extends StaticMethodMatcherPointcut {

    private final  String methodName;
    private final Class targetClass;

    public EchoServicePointCut(String methodName, Class targetClass) {
        this.methodName = methodName;
        this.targetClass = targetClass;
    }




    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        System.out.printf("method name:[%s] ,targetClass : [%s]%n", method.getName(), targetClass.getSimpleName());
        return ObjectUtils.nullSafeEquals(methodName, method.getName())
                && this.targetClass.isAssignableFrom(targetClass);
    }

}
