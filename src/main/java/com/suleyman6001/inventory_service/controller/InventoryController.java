package com.suleyman6001.inventory_service.controller;

import com.suleyman6001.inventory_service.dto.request.ItemCreationRequestDto;
import com.suleyman6001.inventory_service.dto.response.ItemResponseDto;
import com.suleyman6001.inventory_service.dto.request.ReservationRequestDto;
import com.suleyman6001.inventory_service.dto.response.ReservationResponseDto;
import com.suleyman6001.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{productCode}")
    public ResponseEntity<ItemResponseDto> getInventoryItem(@PathVariable String productCode) {
        logger.info("Received the inventory lookup request for product code {}", productCode);
        ItemResponseDto responseDto = inventoryService.getInventoryItem(productCode);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> reserveItem(@Valid @RequestBody ReservationRequestDto reservationRequestDto) {
        ReservationResponseDto responseDto = inventoryService.reserveItem(reservationRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/items")
    public ResponseEntity<ItemResponseDto> createInventoryItem(@Valid @RequestBody ItemCreationRequestDto itemCreationRequestDto) {
        ItemResponseDto responseDto = inventoryService.createInventoryItem(itemCreationRequestDto);

        return ResponseEntity.ok(responseDto);
    }
}
