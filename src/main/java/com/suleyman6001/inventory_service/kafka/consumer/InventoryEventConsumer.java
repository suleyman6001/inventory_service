package com.suleyman6001.inventory_service.kafka.consumer;

import com.suleyman6001.inventory_service.kafka.event.InventoryItemReservationResultEvent;
import com.suleyman6001.inventory_service.kafka.event.OrderCreatedEvent;
import com.suleyman6001.inventory_service.kafka.producer.InventoryEventProducer;
import com.suleyman6001.inventory_service.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryEventConsumer {
    private static final Logger logger = LoggerFactory.getLogger(InventoryEventConsumer.class);
    private final InventoryRepository inventoryRepository;
    private final InventoryEventProducer inventoryEventProducer;

    @Autowired
    public InventoryEventConsumer(InventoryRepository inventoryRepository, InventoryEventProducer inventoryEventProducer) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryEventProducer = inventoryEventProducer;
    }

    @KafkaListener(topics = "${app.kafka.topics.order-created}", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void consumeOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        logger.info("Inside consumeOrderCreatedEvent() method");
        logger.info(orderCreatedEvent.toString());

        Long orderId = orderCreatedEvent.getOrderId();
        String productCode = orderCreatedEvent.getProductCode();
        Integer requestedQuantity = orderCreatedEvent.getRequestedQuantity();
        String customerId = orderCreatedEvent.getCustomerId();

        InventoryItemReservationResultEvent reservationResultEvent = new InventoryItemReservationResultEvent();
        reservationResultEvent.setOrderId(orderId);
        reservationResultEvent.setProductCode(productCode);
        reservationResultEvent.setRequestedQuantity(requestedQuantity);
        reservationResultEvent.setCustomerId(customerId);
        reservationResultEvent.setReserved(false);

        // Handling reservation failure
        // Item does not exist
        if (!inventoryRepository.existsByProductCode(productCode)) {
            reservationResultEvent.setMessage("Item with given product code couldn't be found. Reservation failed!");
            inventoryEventProducer.sendInventoryItemReservationResultEvent(reservationResultEvent);
            return;
        }

        int rows = inventoryRepository.reserveStock(productCode, requestedQuantity);

        // Not enough stock logic
        if (rows != 1) {
            reservationResultEvent.setMessage("Insufficient stock for the Item with given product code. Reservation failed!");
            inventoryEventProducer.sendInventoryItemReservationResultEvent(reservationResultEvent);
            return;
        }

        // Reservation successful
        reservationResultEvent.setReserved(true);
        reservationResultEvent.setMessage("Reservation successful");
        inventoryEventProducer.sendInventoryItemReservationResultEvent(reservationResultEvent);
    }
}
