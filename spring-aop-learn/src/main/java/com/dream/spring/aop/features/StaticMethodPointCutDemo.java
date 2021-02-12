package com.dream.spring.aop.features;

import com.dream.spring.aop.interceptor.EchoServiceMethodInterceptor;
import com.dream.spring.aop.service.EchoService;
import com.dream.spring.aop.service.impl.DefaultEchoService;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * <p>
 * PointCut 进行筛选判断 Advice进行操作 PointCut 与 Advice 需要Advisor 进行承载
 * </p>
 *
 * @author lim
 * @date 2021/2/12 16:11
 * @className StaticMethodPointCutDemo
 * @desc DefaultAdvisorChainFactory
 */
public class StaticMethodPointCutDemo {
    public static void main(String[] args) {
        EchoServicePointCut pointCut = new EchoServicePointCut("hello", EchoService.class);

        // 将 PointCut 适配成 AdvisorDefault
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointCut,new EchoServiceMethodInterceptor());

        EchoService echoService = new DefaultEchoService();
        ProxyFactory proxyFactory = new ProxyFactory(echoService);

        proxyFactory.addAdvisor(advisor);

        EchoService echoServiceProxy = (EchoService) proxyFactory.getProxy();
        System.out.println(echoServiceProxy.hello("StaticMethodMatcherPointcut"));
    }
}

