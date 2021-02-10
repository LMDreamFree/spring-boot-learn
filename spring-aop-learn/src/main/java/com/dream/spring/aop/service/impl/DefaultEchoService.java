package com.dream.spring.aop.service.impl;

import com.dream.spring.aop.service.EchoService;

/**
 *
 * @author lim
 * @date 2021/2/10 15:07
 * @className DefaultEchoService
 */
public class DefaultEchoService  implements EchoService {
    @Override
    public String hello(String name) {
        return "[ECHO : ] " + name;
    }
}
