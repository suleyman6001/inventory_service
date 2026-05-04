package com.suleyman6001.inventory_service.kafka.consumer;

import com.suleyman6001.inventory_service.kafka.event.OrderCreatedEvent;
import com.suleyman6001.inventory_service.repository.InventoryRepository;
import com.suleyman6001.inventory_service.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventConsumer {
    private static final Logger logger = LoggerFactory.getLogger(InventoryEventConsumer.class);
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryEventConsumer(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @KafkaListener(topics = "${app.kafka.topics.order-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        logger.info("Received OrderCreatedEvent:");
        logger.info("orderId = {}", orderCreatedEvent.orderId());
        logger.info("productCode = {}", orderCreatedEvent.productCode());
        logger.info("quantity = {}", orderCreatedEvent.requestedQuantity());
        logger.info("Customer id: {}", orderCreatedEvent.customerId());

        int rows = inventoryRepository.reserveStock(orderCreatedEvent.productCode(), orderCreatedEvent.requestedQuantity());

        if (rows != 1) {
            // TODO Failed reservation logic
        }


    }
}
