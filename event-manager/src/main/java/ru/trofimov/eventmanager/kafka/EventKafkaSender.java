package ru.trofimov.eventmanager.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import ru.trofimov.common.dto.EventChangeKafkaMessage;

import java.util.concurrent.CompletableFuture;

@Component
public class EventKafkaSender {

    private final static Logger log = LoggerFactory.getLogger(EventKafkaSender.class);

    private final KafkaTemplate<Long, EventChangeKafkaMessage> kafkaTemplate;

    public EventKafkaSender(KafkaTemplate<Long, EventChangeKafkaMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(EventChangeKafkaMessage eventChangeKafkaMessage) {
        log.info("Sending event: event={}", eventChangeKafkaMessage);

        CompletableFuture<SendResult<Long, EventChangeKafkaMessage>> sendEvent = kafkaTemplate.send(
                "event-topic",
                eventChangeKafkaMessage.getEventId(),
                eventChangeKafkaMessage
        );

        sendEvent.thenAccept(result ->
                log.info("Event sent successfully: eventId={}, partition={}",
                        eventChangeKafkaMessage.getEventId(),
                        result.getRecordMetadata().partition())
        ).exceptionally(ex -> {
            log.error("Failed to send event: eventId={}, error={}",
                    eventChangeKafkaMessage.getEventId(),
                    ex.getMessage(), ex);
            return null;
        });
    }
}
