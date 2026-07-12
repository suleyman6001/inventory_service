package com.suleyman6001.inventory_service;

import com.suleyman6001.inventory_service.dto.request.ReservationRequestDto;
import com.suleyman6001.inventory_service.dto.response.ReservationResponseDto;
import com.suleyman6001.inventory_service.repository.InventoryRepository;
import com.suleyman6001.inventory_service.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void reserveItem_shouldReturnSuccess_whenStockIsAvailable() {
        // Arrange
        ReservationRequestDto request =
                new ReservationRequestDto(" abc-123 ", 3);

        when(inventoryRepository.reserveStock("ABC-123", 3))
                .thenReturn(1);

        // Act
        ReservationResponseDto response =
                inventoryService.reserveItem(request);

        // Assert
        assertTrue(response.isReserved());
        assertEquals("ABC-123", response.getProductCode());
        assertEquals(3, response.getRequestedQuantity());
        assertEquals(
                "Stock reserved successfully!",
                response.getMessage()
        );

        verify(inventoryRepository)
                .reserveStock("ABC-123", 3);

        verify(inventoryRepository, never())
                .existsByProductCode(anyString());
    }

    @Test
    void reserveItem_shouldReturnInsufficientStock_whenProductExistsButStockIsLow() {
        // Arrange
        ReservationRequestDto request =
                new ReservationRequestDto("abc-123", 20);

        when(inventoryRepository.reserveStock("ABC-123", 20))
                .thenReturn(0);

        when(inventoryRepository.existsByProductCode("ABC-123"))
                .thenReturn(true);

        // Act
        ReservationResponseDto response =
                inventoryService.reserveItem(request);

        // Assert
        assertFalse(response.isReserved());
        assertEquals("ABC-123", response.getProductCode());
        assertEquals(20, response.getRequestedQuantity());
        assertEquals(
                "Insufficient Stock to fulfill the reservation!",
                response.getMessage()
        );

        verify(inventoryRepository)
                .reserveStock("ABC-123", 20);

        verify(inventoryRepository)
                .existsByProductCode("ABC-123");
    }

}
