package com.dream.rpc.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * @author cloud
 * @date 2021/7/3 23:21
 * @className ClientInvocationHandler
 */
public class ClientInvocationHandler implements InvocationHandler {

    private final String requestUrl;

    private final String serviceName;

    private final Class<?> returnType;

    private BeanFactory beanFactory;

    private RestTemplate restTemplate = new RestTemplate();

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ClientInvocationHandler(String requestUrl, String serviceName, Class<?> returnType) {
        this.requestUrl = requestUrl;
        this.serviceName = serviceName;
        this.returnType = returnType;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        DiscoveryClient discoveryClient = beanFactory.getBean(DiscoveryClient.class);
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        Optional<ServiceInstance> any = instances.stream().findAny();
        if (!any.isPresent()) {
            throw new RuntimeException("无法获取服务:" + serviceName);
        }
        ServiceInstance serviceInstance = any.get();
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String request = "http://" + host + ":" + port + requestUrl;
        // http://localhost:8081/send/{message}
        return restTemplate.getForObject(request, returnType, args);
    }
}
