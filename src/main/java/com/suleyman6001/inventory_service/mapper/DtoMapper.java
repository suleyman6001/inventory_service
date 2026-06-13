package com.suleyman6001.inventory_service.mapper;

import com.suleyman6001.inventory_service.dto.request.ItemCreationRequestDto;
import com.suleyman6001.inventory_service.dto.response.ItemResponseDto;
import com.suleyman6001.inventory_service.entity.InventoryItem;

public class DtoMapper {
    public static ItemResponseDto fromEntityToDto(InventoryItem item) {
        ItemResponseDto responseDto = new ItemResponseDto();

        responseDto.setProductCode(item.getProductCode().trim().toUpperCase());
        responseDto.setItemId(item.getId());
        responseDto.setProductName(item.getProductName());
        responseDto.setPrice(item.getPrice());
        responseDto.setAvailableQuantity(item.getAvailableQuantity());
        responseDto.setReservedQuantity(item.getReservedQuantity());

        return responseDto;
    }

    public static InventoryItem fromDtoToEntity(ItemCreationRequestDto itemCreationRequestDto) {
        InventoryItem item = new InventoryItem();

        item.setProductCode(itemCreationRequestDto.getProductCode().trim().toUpperCase());
        item.setProductName(itemCreationRequestDto.getProductName());
        item.setPrice(itemCreationRequestDto.getPrice());
        item.setAvailableQuantity(itemCreationRequestDto.getAvailableQuantity());

        return item;
    }
}
