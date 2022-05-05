package com.streams.newcloud.producers;

import com.streams.newcloud.dto.User;
import io.cloudevents.extensions.ExtensionFormat;
import io.cloudevents.extensions.InMemoryFormat;
import io.cloudevents.v1.CloudEventBuilder;
import io.cloudevents.v1.CloudEventImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class Producer {

    private final StreamBridge streamBridge;

    private final String DEMO_OUT = "demo-out-0";

    @Scheduled(fixedDelay = 5000L)
    public void sendMessage(){

        InMemoryFormat tcxAccountId = InMemoryFormat.of("x-credential-account-id", "89397", String.class);
        InMemoryFormat tcxUserName = InMemoryFormat.of("x-credential-username", "Mothi", String.class);

        CloudEventImpl<Object> cloudEvent = CloudEventBuilder.builder()
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("/mothi"))
                .withType("eventType")
                .withExtension(ExtensionFormat.of(tcxAccountId))
                .withExtension(ExtensionFormat.of(tcxUserName))
                .withDataContentType("application/json")
                .withData(new User("mothi", "SE2" , Date.from(Instant.now())))
                .build();

        log.info("Message to produce");
        streamBridge.send(DEMO_OUT, MessageBuilder.withPayload(cloudEvent)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                .build());

        log.info("Message sent");

    }
}
