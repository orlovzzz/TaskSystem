package org.diploma.notificationservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.diploma.notificationservice.app.api.MessageHandler;
import org.diploma.notificationservice.domain.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {
    private final ObjectMapper objectMapper;
    private final List<MessageHandler> handlers;

    @KafkaListener(topics = "notifications", groupId = "notification-group")
    public void listen(String data) throws JsonProcessingException {
        var message = objectMapper.readValue(data, Message.class);
        log.info("Received message {}", message);
        handlers.stream()
            .filter(h -> h.canHandle(message))
            .findFirst()
            .ifPresent(handler -> handler.handle(message));
    }
}
