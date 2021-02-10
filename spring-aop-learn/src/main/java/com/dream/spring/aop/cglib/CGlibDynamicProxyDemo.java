package com.dream.spring.aop.cglib;

import com.dream.spring.aop.service.EchoService;
import com.dream.spring.aop.service.impl.DefaultEchoService;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * <p>
 * CGlib 动态代理示例
 * </p>
 *
 * @author lim
 * @date 2021/2/10 15:05
 * @className CGlibDynamicProxyDemo
 */
public class CGlibDynamicProxyDemo {
    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
        // 指定super class
        Class<DefaultEchoService> clazz = DefaultEchoService.class;

        enhancer.setSuperclass(clazz);
        // 指定拦截接口
        enhancer.setInterfaces(new Class[]{EchoService.class});

        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object source, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("start : " + System.nanoTime());
                // source 是 CGLib生成的子类
//                Object result = method.invoke(source,args);
                Object result = methodProxy.invokeSuper(source, args);
                System.out.println("end : " + System.nanoTime());
                return result;
            }
        });
        EchoService echoService = (EchoService)enhancer.create();
        String springAop = echoService.hello("spring aop");
        System.out.println(springAop);
    }
}
