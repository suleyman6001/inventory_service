package com.suleyman6001.inventory_service.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ItemCreationRequestDto {

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
    @DecimalMin(value = "0.00", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Integer availableQuantity;

    public ItemCreationRequestDto() {
    }

    public ItemCreationRequestDto(String productCode, String productName, BigDecimal price, Integer availableQuantity) {
        this.productCode = productCode;
        this.productName = productName;
        this.price = price;
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

    @Override
    public String toString() {
        return "ItemCreationRequestDto{" +
                "productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", availableQuantity=" + availableQuantity +
                '}';
    }
}
