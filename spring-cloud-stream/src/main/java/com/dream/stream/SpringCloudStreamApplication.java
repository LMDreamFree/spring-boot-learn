package com.dream.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Supplier;
/**
 * {@link https://github.com/spring-cloud/spring-cloud-stream-samples/blob/main/multi-functions-samples/multi-functions-rabbit/src/main/java/demo/MultipleFunctionsApplication.java}
 * <p>spring cloud stream demo</p>
 * @author: lim
 * @date: 2021/5/9 8:21
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudStreamApplication {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public static final IdGenerator GENERATOR = new AlternativeJdkIdGenerator();

    public static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    BlockingQueue unbounded = new LinkedBlockingQueue<>();

    private final StreamBridge streamBridge;

    private final SubscribableChannel messageChannel;

    public SpringCloudStreamApplication(StreamBridge streamBridge, SubscribableChannel messageChannel) {
        this.streamBridge = streamBridge;
        this.messageChannel = messageChannel;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamApplication.class, args);
    }

    @Bean
    public Consumer<String> sink1() {
        return message -> {
            System.err.println("******************");
            System.err.println("At Sink1");
            System.err.println("******************");
            System.err.println("Received message " + message);
        };
    }

    @Bean
    public Consumer<String> sink2() {
        return message -> {
            System.err.println("******************");
            System.err.println("At Sink2");
            System.err.println("******************");
            System.err.println("Received message " + message);
        };
    }

    @Bean
    public Supplier<String> source1() {
        return () -> {
            String message = "FromSource1";
            System.out.println("******************");
            System.out.println("From Source1");
            System.out.println("******************");
            System.out.println("Sending value: " + message);
            return message;

        };
    }

    @Bean
    public Supplier<String> source2() {
        return () -> {
            String message = "FromSource2";
            System.out.println("******************");
            System.out.println("From Source2");
            System.out.println("******************");
            System.out.println("Sending value: " + message);
            return message;
        };
    }


}
