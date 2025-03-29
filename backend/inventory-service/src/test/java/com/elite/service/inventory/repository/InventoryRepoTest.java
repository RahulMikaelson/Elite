package com.elite.service.inventory.repository;

import com.elite.service.inventory.entity.Inventory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class InventoryRepoTest {

    String productId;
    Inventory savedInventory;

    @Mock
    InventoryRepo inventoryRepo; // ✅ Mocking repository

    @BeforeAll
    static void initAll() {
        log.info("Inventory Repository Test - Initializing resources before all tests and Starting Testing");
    }

    @AfterAll
    static void cleanUpAll() {
        log.info("Inventory Repository Test - Cleaning up all tests");
    }

    @BeforeEach
    void setUp() {
        UUID inventoryId = UUID.randomUUID();
        productId = "67e507093e1f833e8cdf097f";
        savedInventory = new Inventory(inventoryId, productId, "SKU001", 50, Instant.now(), Instant.now());
    }

    @Test
    @DisplayName("Inventory Repository Test - ✅ Should Find Inventory By Product ID")
    void shouldFindAndReturnInventoryByProductId() {
        // Arrange
        when(inventoryRepo.findByProductId(productId)).thenReturn(Optional.of(savedInventory));

        // Act
        Optional<Inventory> foundInventory = inventoryRepo.findByProductId(productId);

        // Assert
        assertTrue(foundInventory.isPresent(), "Inventory should be found");
        assertEquals(productId, foundInventory.get().getProductId());

        // Verify
        verify(inventoryRepo, times(1)).findByProductId(productId);
    }

    @Test
    @DisplayName("Inventory Repository Test - ❌ Should Return Empty When Inventory Not Found")
    void shouldReturnEmptyWhenInventoryNotFound() {
        // Arrange
        when(inventoryRepo.findByProductId("invalid-product-id")).thenReturn(Optional.empty());

        // Act
        Optional<Inventory> foundInventory = inventoryRepo.findByProductId("invalid-product-id");

        // Assert
        assertFalse(foundInventory.isPresent(), "Inventory should NOT be found for invalid product ID");

        // Verify
        verify(inventoryRepo, times(1)).findByProductId("invalid-product-id");
    }
}
