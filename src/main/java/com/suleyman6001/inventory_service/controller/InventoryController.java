package com.suleyman6001.inventory_service.controller;

import com.suleyman6001.inventory_service.dto.request.InventoryItemDto;
import com.suleyman6001.inventory_service.dto.response.ItemCreationResponseDto;
import com.suleyman6001.inventory_service.dto.request.ReservationRequestDto;
import com.suleyman6001.inventory_service.dto.response.ReservationResponseDto;
import com.suleyman6001.inventory_service.entity.InventoryItem;
import com.suleyman6001.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping_platform/inventory_service")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{productCode}")
    public InventoryItem getInventoryItem(@PathVariable String productCode) {
        logger.info("Received the order from client");
        return inventoryService.getInventoryItem(productCode);
    }

    @PostMapping("/reserve")
    public ResponseEntity<ReservationResponseDto> reserveItem(@Valid @RequestBody ReservationRequestDto reservationRequestDto) {
        ReservationResponseDto responseDto = inventoryService.reserveItem(reservationRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/createInventoryItem")
    public ResponseEntity<ItemCreationResponseDto> createInventoryItem(@Valid @RequestBody InventoryItemDto inventoryItemDto) {
        ItemCreationResponseDto responseDto = inventoryService.createInventoryItem(inventoryItemDto);

        return ResponseEntity.ok(responseDto);
    }
}
