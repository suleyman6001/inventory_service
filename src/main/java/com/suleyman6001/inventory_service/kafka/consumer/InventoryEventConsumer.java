package com.suleyman6001.inventory_service.kafka.consumer;

import com.suleyman6001.inventory_service.kafka.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventConsumer {
    private static final Logger logger = LoggerFactory.getLogger(InventoryEventConsumer.class);

    @KafkaListener(topics = "${app.kafka.topics.order-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {
        logger.info("Received OrderCreatedEvent:");
        logger.info("orderId = {}", event.orderId());
        logger.info("productCode = {}", event.productCode());
        logger.info("quantity = {}", event.quantity());
        logger.info("Customer id: {}", event.customerId());
    }
}
