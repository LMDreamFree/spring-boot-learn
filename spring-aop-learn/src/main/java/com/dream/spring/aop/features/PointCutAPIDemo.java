package com.dream.spring.aop.features;

import com.dream.spring.aop.interceptor.EchoServiceMethodInterceptor;
import com.dream.spring.aop.service.EchoService;
import com.dream.spring.aop.service.impl.DefaultEchoService;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lim
 * @date 2021/2/13 17:33
 * @className PointCutAPIDemo
 * @desc TODO
 */
public class PointCutAPIDemo {
    public static void main(String[] args) {
        EchoServiceMethodPointCut pointCut = EchoServiceMethodPointCut.INSTANCE;

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointCut,new EchoServiceMethodInterceptor());

        EchoService echoService = new DefaultEchoService();

        ProxyFactory proxyFactory = new ProxyFactory(echoService);
        proxyFactory.addAdvisor(advisor);

        EchoService proxy = (EchoService) proxyFactory.getProxy();
        System.out.println(proxy.hello("Pointcut"));
    }
}
