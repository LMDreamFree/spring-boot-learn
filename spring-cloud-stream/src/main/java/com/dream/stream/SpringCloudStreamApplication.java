package com.dream.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Supplier;
/**
 * {@link <a href="https://github.com/spring-cloud/spring-cloud-stream-samples/blob/main/multi-functions-samples/multi-functions-rabbit/src/main/java/demo/MultipleFunctionsApplication.java">...</a>}
 * <p>spring cloud stream demo</p>
 * @author: lim
 * @date: 2021/5/9 8:21
 */
@RestController
@EnableDiscoveryClient
@SpringBootApplication
@EnableBinding({Source.class, Sink.class})
public class SpringCloudStreamApplication {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public static final IdGenerator GENERATOR = new AlternativeJdkIdGenerator();

    public static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    BlockingQueue unbounded = new LinkedBlockingQueue<>();

    private final StreamBridge streamBridge;

    @Autowired
    private Source source;

    @GetMapping(value = "/send")
    public String stream(){
        Message<String> message = MessageBuilder.withPayload("Hello World" + LocalDateTime.now()).build();
        source.output().send(message);
        return message.toString();
    }

    @StreamListener(target = Sink.INPUT)
    public void  listener(Message<String> message){
        System.err.println(message);
    }

    public SpringCloudStreamApplication(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamApplication.class, args);
    }

    @GetMapping(value = "/send/{message}")
    public String send(@PathVariable(value = "message") String message){
        boolean result = streamBridge.send("test1", message);
        return result + " : " + message;
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
