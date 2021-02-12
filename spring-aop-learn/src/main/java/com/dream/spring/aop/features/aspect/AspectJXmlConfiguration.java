package com.dream.spring.aop.features.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 *
 * @author lim
 * @date 2021/2/10 23:38
 * @className AspectJConfiguration
 * @desc xml aspect 配置
 */
public class AspectJXmlConfiguration {

    public void before(JoinPoint joinPoint){
        System.out.println("XML Before 拦截 " + joinPoint.getSignature().getName());
    }
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("XML Around 拦截 " + joinPoint.getSignature().getName());
        return joinPoint.proceed();
    }
}
