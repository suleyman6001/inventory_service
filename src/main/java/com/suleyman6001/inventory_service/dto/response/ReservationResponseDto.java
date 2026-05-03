package com.suleyman6001.inventory_service.dto.response;

public class ReservationResponseDto {
    private String productCode;
    private Integer requestedQuantity;
    private boolean isReserved;
    private String message;

    public ReservationResponseDto() {
    }

    public ReservationResponseDto(String productCode, Integer requestedQuantity, boolean isReserved, String message) {
        this.productCode = productCode;
        this.requestedQuantity = requestedQuantity;
        this.isReserved = isReserved;
        this.message = message;
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

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
