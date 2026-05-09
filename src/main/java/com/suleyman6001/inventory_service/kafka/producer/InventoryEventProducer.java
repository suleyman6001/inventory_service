package com.suleyman6001.inventory_service.kafka.producer;

import com.suleyman6001.inventory_service.kafka.event.InventoryItemReservationResultEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventProducer {
    private static final Logger logger = LoggerFactory.getLogger(InventoryEventProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String reservationResultTopic;

    @Autowired
    public InventoryEventProducer(KafkaTemplate<String, Object> kafkaTemplate,
                                  @Value("${app.kafka.topics.inventory-item-reservation-result}") String reservationResultTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.reservationResultTopic = reservationResultTopic;
    }

    public void sendInventoryItemReservationResultEvent(InventoryItemReservationResultEvent reservationResultEvent) {
        kafkaTemplate.send(reservationResultTopic, String.valueOf(reservationResultEvent.getOrderId()), reservationResultEvent);
    }

}
