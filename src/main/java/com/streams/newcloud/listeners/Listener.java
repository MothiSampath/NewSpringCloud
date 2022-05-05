package com.streams.newcloud.listeners;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streams.newcloud.dto.User;
import io.cloudevents.v1.CloudEventImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class Listener {

    @Bean
    public Consumer<Message<CloudEventImpl<User>>> demobinding() {
        return msg -> {
            System.out.println(msg);
        };
    }
}
