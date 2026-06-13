package com.suleyman6001.inventory_service.service;

import com.suleyman6001.inventory_service.dto.request.ItemCreationRequestDto;
import com.suleyman6001.inventory_service.dto.response.ItemResponseDto;
import com.suleyman6001.inventory_service.dto.request.ReservationRequestDto;
import com.suleyman6001.inventory_service.dto.response.ReservationResponseDto;
import com.suleyman6001.inventory_service.entity.InventoryItem;
import com.suleyman6001.inventory_service.mapper.DtoMapper;
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

        ItemResponseDto itemResponseDto;
        Optional<InventoryItem> itemOptional = inventoryRepository.findByProductCode(normalizedProductCode);

        // Object does not exist
        if (itemOptional.isEmpty()) {
            itemResponseDto = new ItemResponseDto();
            itemResponseDto.setProductCode(normalizedProductCode);
            itemResponseDto.setMessage("Item with given product-code does not exist!");

            return itemResponseDto;
        }

        // Object retrieved
        InventoryItem item = itemOptional.get();
        itemResponseDto = DtoMapper.fromEntityToDto(item);
        itemResponseDto.setMessage("Item with given product-code retrieved successfully");

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
    public ItemResponseDto createOrUpdateInventoryItem(ItemCreationRequestDto itemCreationRequestDto) {
        logger.info("Attempting to create or update quantity of the inventory item");

        ItemResponseDto creationResponseDto;
        String normalizedProductCode = itemCreationRequestDto.getProductCode().trim().toUpperCase();

        Optional<InventoryItem> itemOptional = inventoryRepository.findByProductCode(normalizedProductCode);

        // Update the available quantity if the item already exists
        if (itemOptional.isPresent()) {
            InventoryItem inventoryItem = itemOptional.get();
            // updating the properties of the inventory item
            inventoryItem.setProductName(itemCreationRequestDto.getProductName());
            inventoryItem.setPrice(itemCreationRequestDto.getPrice());
            inventoryItem.setAvailableQuantity(itemCreationRequestDto.getAvailableQuantity());

            inventoryRepository.save(inventoryItem);

            creationResponseDto = DtoMapper.fromEntityToDto(inventoryItem);
            creationResponseDto.setMessage("Item with given product number already exists! Item quantity has been updated");

            return creationResponseDto;
        }

        // Creating and persisting inventory item
        InventoryItem item = DtoMapper.fromDtoToEntity(itemCreationRequestDto);
        item.setReservedQuantity(0);

        inventoryRepository.save(item);

        // Preparing a response DTO
        creationResponseDto = DtoMapper.fromEntityToDto(item);
        creationResponseDto.setMessage("Item created successfully!");

        return creationResponseDto;
    }
}
