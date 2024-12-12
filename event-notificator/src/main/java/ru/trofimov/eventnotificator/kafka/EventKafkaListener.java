package ru.trofimov.eventnotificator.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.trofimov.common.dto.EventChangeKafkaMessage;
import ru.trofimov.eventnotificator.service.EventChangeKafkaMessageHandler;

@Component
public class EventKafkaListener {

    private final static Logger log = LoggerFactory.getLogger(EventKafkaListener.class);
    private final EventChangeKafkaMessageHandler eventChangeKafkaMessageHandler;

    public EventKafkaListener(EventChangeKafkaMessageHandler eventChangeKafkaMessageHandler) {
        this.eventChangeKafkaMessageHandler = eventChangeKafkaMessageHandler;
    }

    @KafkaListener(topics = "event-topic", containerFactory = "containerFactory")
    public void listenEvents(
            ConsumerRecord<Long, EventChangeKafkaMessage> record
    ) {
        log.info("Received event: event={}", record.value());
        EventChangeKafkaMessage kafkaMessage = record.value();
        eventChangeKafkaMessageHandler.handleEventChangeMessage(kafkaMessage);
    }
}
