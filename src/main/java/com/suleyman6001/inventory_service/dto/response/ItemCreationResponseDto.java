package com.suleyman6001.inventory_service.dto.response;

public class ItemCreationResponseDto {
    private String productCode;
    private Boolean created;
    private String message;

    public ItemCreationResponseDto() {
    }

    public ItemCreationResponseDto(String productCode, Boolean created, String message) {
        this.productCode = productCode;
        this.created = created;
        this.message = message;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Boolean getCreated() {
        return created;
    }

    public void setCreated(Boolean created) {
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
