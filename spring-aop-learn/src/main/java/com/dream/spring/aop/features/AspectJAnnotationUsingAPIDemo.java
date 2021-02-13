package com.dream.spring.aop.features;

import com.dream.spring.aop.features.aspect.AspectJConfiguration;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lim
 * @date 2021/2/10 23:19
 * @className AspectJDemo
 * {@link AspectJProxyFactory}
 */
public class AspectJAnnotationUsingAPIDemo {
    public static void main(String[] args) {
        // 通过创建HashMap 缓存
        Map<String,Object> cache  = new HashMap<>(2);
        // 创建AspectJ 工厂
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(cache);
        // 增加AspectJ 配置类
        proxyFactory.addAspect(AspectJConfiguration.class);

        // Before Advice 不需要主动触发
        proxyFactory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                if (ObjectUtils.nullSafeEquals("put",method.getName()) && args.length == 2){
                    System.out.printf("当前存放的Key : %s , Value : %s %n",args[0],args[1]);
                }
            }
        });
        // 添加AfterReturn
        proxyFactory.addAdvice(new AfterReturningAdvice() {
            @Override
            public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
                if (ObjectUtils.nullSafeEquals("put",method.getName()) && args.length == 2){
                    System.out.printf("afterReturning():当前存放的Key : %s , Value : %s %n",args[0],args[1]);
                }
            }
        });
//        cache.put("hello","world");
        Map<String,Object> proxyMap = proxyFactory.getProxy();
        // 通过代理对象进行存贮数据 代理才会生效
        proxyMap.put("aop","java");
        Object aop = proxyMap.get("aop");
        System.out.println(aop);
    }
}
