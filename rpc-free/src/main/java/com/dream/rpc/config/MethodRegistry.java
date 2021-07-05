package com.dream.rpc.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cloud
 * @date 2021/7/3 23:22
 * @className MethodRegistry
 */
public abstract class MethodRegistry {

    private final static Map<Method, ClientInvocationHandler> registry = new ConcurrentHashMap<>();

    public static void addRegistry(Method targetMethod, ClientInvocationHandler invocationHandler) {
        registry.putIfAbsent(targetMethod, invocationHandler);
    }

    public static InvocationHandler getInvocationHandler(Method targetMethod) {
        if (registry.containsKey(targetMethod)) {
            return registry.get(targetMethod);
        }
        throw new IllegalAccessError("unknown method");
    }
}
