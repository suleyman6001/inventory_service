package com.suleyman6001.inventory_service.kafka.event;

public class OrderCreatedEvent {
    private Long orderId;
    private String productCode;
    private Integer requestedQuantity;
    private String customerId;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(Long orderId, String productCode, Integer requestedQuantity, String customerId) {
        this.orderId = orderId;
        this.productCode = productCode;
        this.requestedQuantity = requestedQuantity;
        this.customerId = customerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "orderId=" + orderId +
                ", productCode='" + productCode + '\'' +
                ", requestedQuantity=" + requestedQuantity +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
