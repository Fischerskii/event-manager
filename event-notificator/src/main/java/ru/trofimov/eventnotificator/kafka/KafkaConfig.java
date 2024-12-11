package ru.trofimov.eventnotificator.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import ru.trofimov.common.dto.EventChangeKafkaMessage;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, EventChangeKafkaMessage> containerFactory(
            ConsumerFactory<Long, EventChangeKafkaMessage> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<Long, EventChangeKafkaMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
