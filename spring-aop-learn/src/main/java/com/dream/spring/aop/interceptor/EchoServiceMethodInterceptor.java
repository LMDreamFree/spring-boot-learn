package com.dream.spring.aop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 *
 * @author lim
 * @date 2021/2/11 0:01
 * @className EchoServiceMethodInterceptor
 */
public class EchoServiceMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        System.out.println("拦截 EchoService 的方法 ： "  + method);
        return invocation.proceed();
    }
}
