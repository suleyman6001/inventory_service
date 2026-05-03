package com.suleyman6001.inventory_service.service;

import com.suleyman6001.inventory_service.dto.request.InventoryItemDto;
import com.suleyman6001.inventory_service.dto.response.ItemCreationResponseDto;
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

    public InventoryItem getInventoryItem(String productCode) {
        logger.info("Retrieving the inventory item with the given product code");
        String normalizedProductCode = productCode.trim().toUpperCase();
        Optional<InventoryItem> inventoryItemOptional = inventoryRepository.findByProductCode(normalizedProductCode);

        return inventoryItemOptional.orElse(null);

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
    public ItemCreationResponseDto createInventoryItem(InventoryItemDto inventoryItemDto) {
        logger.info("Attempting to reserve the requested quantity of the given product number");

        ItemCreationResponseDto creationResponseDto = new ItemCreationResponseDto();
        String normalizedProductCode = inventoryItemDto.getProductCode().trim().toUpperCase();

        creationResponseDto.setProductCode(normalizedProductCode);

        if (inventoryRepository.existsByProductCode(normalizedProductCode)) {
            creationResponseDto.setCreated(false);
            creationResponseDto.setMessage("Item with given product number already exists!");
            return creationResponseDto;
        }

        InventoryItem item = new InventoryItem();

        item.setProductCode(normalizedProductCode);
        item.setProductName(inventoryItemDto.getProductName());
        item.setAvailableQuantity(inventoryItemDto.getAvailableQuantity());

        inventoryRepository.save(item);
        creationResponseDto.setCreated(true);
        creationResponseDto.setMessage("Item created successfully!");

        return creationResponseDto;
    }
}
