package com.dream.stream.redis;

import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.nio.charset.StandardCharsets;


/**
 * <p>
 * Spring cloud stream redis
 * </p>
 *
 * @author lim
 * @date 2021/11/4 20:44
 * @className RedisMessageChannelBinder
 */
public class RedisMessageChannelBinder implements Binder<MessageChannel, ConsumerProperties, ProducerProperties> {

    //注入restTemplate的时候不指定类型，而在在获取处理器的时候，手动指定泛型即可
    private final RedisTemplate redisTemplate;

    public RedisMessageChannelBinder(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Binding<MessageChannel> bindConsumer(String channelName, String group, MessageChannel inboundBindTarget, ConsumerProperties consumerProperties) {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.subscribe(new MessageListener() {
                    @Override
                    public void onMessage(Message message, byte[] pattern) {
                        String json = new String(message.getBody(), StandardCharsets.UTF_8);
                        org.springframework.messaging.Message<String> build = MessageBuilder.withPayload(json).build();
                        inboundBindTarget.send(build);
                    }
                }, channelName.getBytes(StandardCharsets.UTF_8));
                return null;
            }
        },true);
        return () -> {
        };
    }

    @Override
    public Binding<MessageChannel> bindProducer(String channelName, MessageChannel outboundBindTarget, ProducerProperties producerProperties) {
        if (outboundBindTarget instanceof SubscribableChannel) {
            SubscribableChannel subscribableChannel = (SubscribableChannel) outboundBindTarget;
            subscribableChannel.subscribe(message -> {
                // 接受MessageChannel#send 发送的消息
                String json = " {\"id\":-7372052908061786868,\"creatorId\":null,\"creator\":\"叫我墨罹丶\",\"createTime\":null,\"updateId\":null,\"update\":null,\"updateTime\":null,\"orderNo\":\"-1740174272813849038\",\"orderAmount\":null,\"discountAmount\":null,\"totalAmount\":null}";
                redisTemplate.convertAndSend(channelName,json);
                System.out.println("response :" + json);
            });
        }
        return () -> {
        };
    }
}
