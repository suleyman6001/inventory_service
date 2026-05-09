package com.suleyman6001.inventory_service.kafka.event;

public class InventoryItemReservationResultEvent {
    private Long orderId;
    private String productCode;
    private Integer requestedQuantity;
    private String customerId;
    private Boolean reserved;
    private String message;

    public InventoryItemReservationResultEvent() {
    }

    public InventoryItemReservationResultEvent(Long orderId, String productCode, Integer requestedQuantity, String customerId, Boolean reserved, String message) {
        this.orderId = orderId;
        this.productCode = productCode;
        this.requestedQuantity = requestedQuantity;
        this.customerId = customerId;
        this.reserved = reserved;
        this.message = message;
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

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "InventoryItemReservationResultEvent{" +
                "orderId=" + orderId +
                ", productCode='" + productCode + '\'' +
                ", requestedQuantity=" + requestedQuantity +
                ", customerId='" + customerId + '\'' +
                ", reserved=" + reserved +
                ", message='" + message + '\'' +
                '}';
    }
}
