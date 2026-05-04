package com.suleyman6001.inventory_service.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Value("${app.kafka.topics.inventory-item-reservation-result}")
    private String inventoryItemReservationResultTopic;

    @Bean
    NewTopic inventoryItemReservationResultTopic() {
        return new NewTopic(inventoryItemReservationResultTopic, 1, (short) 1);
    }
}
