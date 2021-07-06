package com.dream.rpc.client;

import com.dream.rpc.annotation.EnableRpc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author cloud
 * @date 2021/7/3 22:56
 * @className UserClient
 */
@EnableRpc(name = "spring-cloud-stream-demo")
public interface UserClient {

    /**
     * <p>测试自定义远程调用</p>
     * @author cloud
     * @date: 2021/7/3 22:58
     */
    @GetMapping(value = "/send/{message}")
    String send(@PathVariable(value = "message") String message);
}
