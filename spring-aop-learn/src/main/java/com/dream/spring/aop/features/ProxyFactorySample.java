package com.dream.spring.aop.features;

import com.dream.spring.aop.interceptor.EchoServiceMethodInterceptor;
import com.dream.spring.aop.service.EchoService;
import com.dream.spring.aop.service.impl.DefaultEchoService;
import org.springframework.aop.framework.ProxyFactory;

/**
 * {@link ProxyFactory }
 * 属于更为底层的API
 *
 * @author lim
 * @date 2021/2/11 0:16
 * @className ProxyFactorySample
 */
public class ProxyFactorySample {
    public static void main(String[] args) {
        DefaultEchoService echoService = new DefaultEchoService();

        ProxyFactory factory = new ProxyFactory(echoService);

        // 添加Advice 实现 MethodInterceptor extends Advice
        factory.addAdvice(new EchoServiceMethodInterceptor());

        EchoService proxy = (EchoService) factory.getProxy();
        System.out.println(proxy.hello("hello proxyFactory"));
    }
}
