package com.dream.stream.http;

import cn.hutool.http.HttpRequest;
import okhttp3.*;
import org.springframework.cloud.stream.binder.*;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;


/**
 * <p>
 * Spring cloud stream http
 * </p>
 *
 * @author lim
 * @date 2021/11/4 20:44
 * @className HttpMessageChannelBinder
 */
public class HttpMessageChannelBinder implements Binder<MessageChannel, ConsumerProperties, ProducerProperties> {

    private final OkHttpClient okHttpClient;

    public HttpMessageChannelBinder(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    public Binding<MessageChannel> bindConsumer(String name, String group, MessageChannel inboundBindTarget, ConsumerProperties consumerProperties) {
        return null;
    }

    @Override
    public Binding<MessageChannel> bindProducer(String name, MessageChannel outboundBindTarget, ProducerProperties producerProperties) {
        if (outboundBindTarget instanceof SubscribableChannel) {
            SubscribableChannel subscribableChannel = (SubscribableChannel) outboundBindTarget;
            subscribableChannel.subscribe(message -> {
                // 接受MessageChannel#send 发送的消息
                String json = " {\"id\":-7372052908061786868,\"creatorId\":null,\"creator\":\"叫我墨罹丶\",\"createTime\":null,\"updateId\":null,\"update\":null,\"updateTime\":null,\"orderNo\":\"-1740174272813849038\",\"orderAmount\":null,\"discountAmount\":null,\"totalAmount\":null}";
                /*Request.Builder builder = new Request.Builder();

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                builder.post(requestBody);
                Request request = builder.build();*/
                String response =
                        HttpRequest.post("http://localhost:8000/index/stream").timeout(30000).body(json, "application/json; charset=utf-8").execute().body();
                System.out.println("response :" + response);
            });
        }
        return () -> {
        };
    }
}
