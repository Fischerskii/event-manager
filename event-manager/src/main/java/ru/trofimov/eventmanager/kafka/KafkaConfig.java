package ru.trofimov.eventmanager.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.trofimov.common.dto.EventChangeKafkaMessage;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaTemplate<Long, EventChangeKafkaMessage> kafkaTemplate(KafkaProperties kafkaProperties) {
        Map<String, Object> props = kafkaProperties.buildProducerProperties(new DefaultSslBundleRegistry());

        ProducerFactory<Long, EventChangeKafkaMessage> producerFactory =
                new DefaultKafkaProducerFactory<>(props);

        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic eventTopic() {
        return TopicBuilder.name("event-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
