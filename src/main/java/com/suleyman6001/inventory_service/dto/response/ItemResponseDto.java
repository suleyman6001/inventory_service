package com.suleyman6001.inventory_service.dto.response;

import java.math.BigDecimal;

public class ItemResponseDto {
    private String message;

    private Long itemId;
    private String productCode;
    private String productName;
    private BigDecimal price;
    private Integer availableQuantity;
    private Integer reservedQuantity;


    public ItemResponseDto() {
    }

    public ItemResponseDto(String message, Long itemId, String productCode, String productName, BigDecimal price, Integer availableQuantity, Integer reservedQuantity) {
        this.message = message;
        this.itemId = itemId;
        this.productCode = productCode;
        this.productName = productName;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = reservedQuantity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    @Override
    public String toString() {
        return "ItemCreationResponseDto{" +
                "message='" + message + '\'' +
                ", itemId=" + itemId +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", availableQuantity=" + availableQuantity +
                ", reservedQuantity=" + reservedQuantity +
                '}';
    }
}
