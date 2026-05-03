package com.suleyman6001.inventory_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class InventoryItemDto {

    @NotBlank(message = "Product code is required")
    @Size(max = 100, message = "Product code must not exceed 100 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9]+([_-][A-Za-z0-9]+)*$",
            message = "Product code must contain only letters, numbers, hyphens, and underscores"
    )
    private String productCode;
    @NotBlank
    private String productName;
    @NotNull
    private Integer availableQuantity;

    public InventoryItemDto() {
    }

    public InventoryItemDto(String productCode, String productName, Integer availableQuantity) {
        this.productCode = productCode;
        this.productName = productName;
        this.availableQuantity = availableQuantity;
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

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @Override
    public String toString() {
        return "{productCode : " + productCode + ", availableQuantity : " + availableQuantity + "}";
    }

}
