package com.suleyman6001.inventory_service.service;

import com.suleyman6001.inventory_service.dto.request.ItemCreationRequestDto;
import com.suleyman6001.inventory_service.dto.response.ItemResponseDto;
import com.suleyman6001.inventory_service.dto.request.ReservationRequestDto;
import com.suleyman6001.inventory_service.dto.response.ReservationResponseDto;
import com.suleyman6001.inventory_service.entity.InventoryItem;
import com.suleyman6001.inventory_service.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InventoryService {
    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public ItemResponseDto getInventoryItem(String productCode) {
        logger.info("Retrieving the inventory item with the given product code");
        String normalizedProductCode = productCode.trim().toUpperCase();

        ItemResponseDto itemResponseDto = new ItemResponseDto();
        itemResponseDto.setProductCode(normalizedProductCode);

        Optional<InventoryItem> itemOptional = inventoryRepository.findByProductCode(normalizedProductCode);

        if (itemOptional.isEmpty()) {
            itemResponseDto.setMessage("Item with given product-code does not exist!");
            return itemResponseDto;
        }

        InventoryItem item = itemOptional.get();

        itemResponseDto.setMessage("Item with given product-code retrieved successfully");
        itemResponseDto.setItemId(item.getId());
        itemResponseDto.setProductName(item.getProductName());
        itemResponseDto.setPrice(item.getPrice());
        itemResponseDto.setAvailableQuantity(item.getAvailableQuantity());
        itemResponseDto.setReservedQuantity(item.getReservedQuantity());

        return itemResponseDto;
    }

    @Transactional
    public ReservationResponseDto reserveItem(ReservationRequestDto reservationRequestDto) {
        logger.info("Attempting to reserve the requested quantity of the given product number");

        String normalizedProductCode = reservationRequestDto.getProductCode().trim().toUpperCase();
        Integer requestedQuantity = reservationRequestDto.getRequestedQuantity();
        ReservationResponseDto responseDto = new ReservationResponseDto();

        responseDto.setProductCode(normalizedProductCode);
        responseDto.setRequestedQuantity(requestedQuantity);

        int updatedRows = inventoryRepository.reserveStock(normalizedProductCode, requestedQuantity);

        // Successful reservation
        if (updatedRows == 1) {
            responseDto.setReserved(true);
            responseDto.setMessage("Stock reserved successfully!");
            return responseDto;
        }

        // Send reservation failure response if the item is not found
        boolean itemExists = inventoryRepository.existsByProductCode(normalizedProductCode);

        if (!itemExists) {
            responseDto.setMessage("Product with given product name could not be found!");
        }
        // Send reservation failure response if there is not enough available quantity to fulfill the reservation
        else {
            responseDto.setMessage("Insufficient Stock to fulfill the reservation!");
        }
        responseDto.setReserved(false);

        return responseDto;
    }

    @Transactional
    public ItemResponseDto createInventoryItem(ItemCreationRequestDto itemCreationRequestDto) {
        logger.info("Attempting to reserve the requested quantity of the given product number");

        ItemResponseDto creationResponseDto = new ItemResponseDto();
        String normalizedProductCode = itemCreationRequestDto.getProductCode().trim().toUpperCase();

        creationResponseDto.setProductCode(normalizedProductCode);

        if (inventoryRepository.existsByProductCode(normalizedProductCode)) {
            creationResponseDto.setMessage("Item creation unsuccessful. Item with given product number already exists!");
            return creationResponseDto;
        }

        // Creating and persisting inventory item
        InventoryItem item = new InventoryItem();

        item.setProductCode(normalizedProductCode);
        item.setProductName(itemCreationRequestDto.getProductName());
        item.setPrice(itemCreationRequestDto.getPrice());
        item.setAvailableQuantity(itemCreationRequestDto.getAvailableQuantity());
        item.setReservedQuantity(0);

        inventoryRepository.save(item);

        // Preparing a response DTO
        creationResponseDto.setMessage("Item created successfully!");
        creationResponseDto.setItemId(item.getId());
        creationResponseDto.setProductName(item.getProductName());
        creationResponseDto.setPrice(item.getPrice());
        creationResponseDto.setAvailableQuantity(item.getAvailableQuantity());
        creationResponseDto.setReservedQuantity(item.getReservedQuantity());

        return creationResponseDto;
    }
}
