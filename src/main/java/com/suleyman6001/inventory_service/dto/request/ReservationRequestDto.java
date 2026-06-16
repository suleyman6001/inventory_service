package com.suleyman6001.inventory_service.dto.request;

import jakarta.validation.constraints.*;

public class ReservationRequestDto {
    @NotBlank(message = "Product code is required")
    @Size(max = 100, message = "Product code must not exceed 100 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9]+([_-][A-Za-z0-9]+)*$",
            message = "Product code must contain only letters, numbers, hyphens, and underscores"
    )
    private String productCode;
    @NotNull
    @Min(1)
    private Integer requestedQuantity;

    public ReservationRequestDto() {
    }

    public ReservationRequestDto(String productCode, Integer requestedQuantity) {
        this.productCode = productCode;
        this.requestedQuantity = requestedQuantity;
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

    @Override
    public String toString() {
        return "{productCode : " + productCode + ", requestedQuantity : " + requestedQuantity + "}";
    }
}
