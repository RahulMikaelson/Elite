package com.elite.service.inventory.service;

import com.elite.service.inventory.dto.InventoryListResponseDTO;
import com.elite.service.inventory.dto.InventoryRequestDTO;
import com.elite.service.inventory.dto.InventoryResponseDTO;
import com.elite.service.inventory.dto.StockUpdateRequestDTO;
import com.elite.service.inventory.entity.Inventory;
import com.elite.service.inventory.exception.InventoryNotFoundException;
import com.elite.service.inventory.mapper.InventoryMapper;
import com.elite.service.inventory.repository.InventoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class)
class InventoryServiceImplTest {

    String productId;
    Inventory inventory;
    Inventory savedInventory;
    InventoryResponseDTO inventoryResponseDTO;
    List<Inventory> inventoryList;
    StockUpdateRequestDTO stockUpdateRequestDTO;
    InventoryRequestDTO inventoryRequestDTO;
    @Mock
    private InventoryRepo inventoryRepo;
    @Mock
    private InventoryMapper inventoryMapper;
    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @BeforeAll
    static void initAll() {
        log.info("Inventory Service Implementation Test - Initializing resources before all tests and Starting Testing");
    }

    @AfterAll
    static void cleanUpAll() {
        log.info("Inventory Service Implementation Test - Cleaning up all tests");
    }

    @BeforeEach
    void setUp() {
        UUID inventoryId = UUID.randomUUID();
        productId = "67e507093e1f833e8cdf097f";
        inventory = new Inventory(null, productId, "SKU001", 50, Instant.now(), Instant.now());
        savedInventory = new Inventory(inventoryId, productId, "SKU001", 50, Instant.now(), Instant.now());
        inventoryResponseDTO = new InventoryResponseDTO(inventoryId, productId, "SKU001", 50, Instant.now(), Instant.now());
        inventoryList = List.of(savedInventory);
        inventoryRequestDTO = new InventoryRequestDTO(productId, "SKU001", 50);
        stockUpdateRequestDTO = new StockUpdateRequestDTO(10);
    }


    @Test
    @DisplayName("Inventory Service Implementation Test - ✅ Should Return Inventory Response When Found With Product-Id")
    void shouldGetInventoryOfProductByProductId() {
        // Arrange
        when(inventoryRepo.findByProductId("67e507093e1f833e8cdf097f")).thenReturn(Optional.of(savedInventory));
        when(inventoryMapper.inventoryToInventoryResponseDTO(savedInventory)).thenReturn(inventoryResponseDTO);
        // Act
        InventoryResponseDTO productInventory = inventoryService.getProductInventory("67e507093e1f833e8cdf097f");
        // Assert
        assertNotNull(productInventory);
        assertEquals(inventoryResponseDTO.getProductId(), productInventory.getProductId());
        assertEquals(inventoryResponseDTO.getId(), productInventory.getId());
        assertEquals(inventoryResponseDTO.getSku(), productInventory.getSku());
        assertEquals(inventoryResponseDTO.getStock(), productInventory.getStock());
        assertEquals(inventoryResponseDTO.getUpdatedAt(), productInventory.getUpdatedAt());
        assertEquals(inventoryResponseDTO.getCreatedAt(), productInventory.getCreatedAt());
        // Verify
        verify(inventoryRepo, times(1)).findByProductId("67e507093e1f833e8cdf097f");
        verify(inventoryMapper, times(1)).inventoryToInventoryResponseDTO(savedInventory);
    }

    @Test
    @DisplayName("Inventory Service Implementation Test - ❌ Should Throw Inventory Not Found Exception when Inventory Is Not Found With Product-Id")
    void shouldThrowExceptionWhenInventoryIsNotFoundWithProductId() {
        // Arrange
        when(inventoryRepo.findByProductId("test")).thenReturn(Optional.empty());

        // Act & Assert
        InventoryNotFoundException exception = assertThrows(InventoryNotFoundException.class, () -> inventoryService.getProductInventory("test"));

        // Assertions
        assertNotNull(exception);
        assertEquals("Inventory not found for product-id: test", exception.getMessage());

        // Verify
        verify(inventoryRepo, times(1)).findByProductId("test");
        verify(inventoryMapper, times(0)).inventoryToInventoryResponseDTO(any());
    }


    @Test
    @DisplayName("Inventory Service Implementation Test - ✅ Should Fetch And Return All The Inventories")
    void shouldFetchAndReturnAllInventories() {
        // Arrange
        when(inventoryRepo.findAll()).thenReturn(inventoryList);
        when(inventoryMapper.inventoryToInventoryResponseDTO(any())).thenReturn(inventoryResponseDTO);
        // Act
        InventoryListResponseDTO inventoryListResponseDTO = inventoryService.fetchAllInventories();
        // Assert
        assertNotNull(inventoryListResponseDTO);
        assertEquals(inventoryList.size(), inventoryListResponseDTO.getInventoryList().size());
        // Verify
        verify(inventoryRepo, times(1)).findAll();
        verify(inventoryMapper, times(inventoryList.size())).inventoryToInventoryResponseDTO(any());
    }


    @Test
    @DisplayName("Inventory Service Implementation Test - ✅ Should Return Empty List When No Inventories Exists")
    void shouldReturnEmptyListWhenNoInventoriesExist() {
        // Arrange
        when(inventoryRepo.findAll()).thenReturn(Collections.emptyList());
        // Act
        InventoryListResponseDTO inventoryListResponseDTO = inventoryService.fetchAllInventories();
        // Assert
        assertNotNull(inventoryListResponseDTO);
        assertEquals(0, inventoryListResponseDTO.getInventoryList().size());
        // Verify
        verify(inventoryRepo, times(1)).findAll();
        verify(inventoryMapper, times(0)).inventoryToInventoryResponseDTO(any());
    }

    @Test
    @DisplayName("Inventory Service Implementation Test - ✅ Should Return Inventory Response After Saving New Inventory")
    void shouldAddNewInventory() {
        // Arrange
        when(inventoryMapper.inventoryRequestDTOToInventory(inventoryRequestDTO)).thenReturn(inventory);
        when(inventoryRepo.save(inventory)).thenReturn(savedInventory);
        when(inventoryMapper.inventoryToInventoryResponseDTO(savedInventory)).thenReturn(inventoryResponseDTO);
        // Act
        InventoryResponseDTO inventoryResponse = inventoryService.addNewStockEntry(inventoryRequestDTO);
        // Assert
        assertNotNull(inventoryResponse);
        assertEquals(inventoryResponseDTO.getProductId(), savedInventory.getProductId());
        assertEquals(inventoryResponseDTO.getId(), savedInventory.getId());
        assertEquals(inventoryResponseDTO.getSku(), savedInventory.getSku());
        assertEquals(inventoryResponseDTO.getStock(), savedInventory.getStock());
        // Verify
        verify(inventoryMapper, times(1)).inventoryRequestDTOToInventory(inventoryRequestDTO);
        verify(inventoryRepo, times(1)).save(inventory);
        verify(inventoryMapper, times(1)).inventoryToInventoryResponseDTO(savedInventory);
    }

    @Test
    @DisplayName("Inventory Service Implementation Test - ✅ Should Update(Increase) Stock Of An Inventory")
    void shouldUpdateStockForInventory() {
        // Arrange
        when(inventoryRepo.findByProductId(productId)).thenReturn(Optional.of(savedInventory));

        // Create a new updated Inventory object instead of modifying savedInventory directly
        Inventory updatedInventory = new Inventory(
                savedInventory.getId(),
                savedInventory.getProductId(),
                savedInventory.getSku(),
                savedInventory.getStock() + stockUpdateRequestDTO.getQuantity(), // Increase stock
                Instant.now(), // Updated timestamp
                savedInventory.getCreatedAt()
        );

        when(inventoryRepo.save(any())).thenReturn(updatedInventory);
        when(inventoryMapper.inventoryToInventoryResponseDTO(updatedInventory)).thenReturn(
                new InventoryResponseDTO(
                        updatedInventory.getId(),
                        updatedInventory.getProductId(),
                        updatedInventory.getSku(),
                        updatedInventory.getStock(),
                        updatedInventory.getUpdatedAt(),
                        updatedInventory.getCreatedAt()
                )
        );
        // Act
        InventoryResponseDTO inventoryResponse = inventoryService.updateStockEntry(productId, stockUpdateRequestDTO);
        // Assert
        assertNotNull(inventoryResponse);
        assertEquals(updatedInventory.getProductId(), inventoryResponse.getProductId());
        assertEquals(updatedInventory.getId(), inventoryResponse.getId());
        assertEquals(updatedInventory.getSku(), inventoryResponse.getSku());
        assertEquals(updatedInventory.getStock(), inventoryResponse.getStock());

        // Allow some tolerance for timestamp differences
        assertTrue(Math.abs(updatedInventory.getUpdatedAt().toEpochMilli() - inventoryResponse.getUpdatedAt().toEpochMilli()) < 1000);
        assertEquals(updatedInventory.getCreatedAt(), inventoryResponse.getCreatedAt());

        // Verify
        verify(inventoryRepo, times(1)).findByProductId(productId);
        verify(inventoryRepo, times(1)).save(any());
        verify(inventoryMapper, times(1)).inventoryToInventoryResponseDTO(updatedInventory);
    }

    @Test
    @DisplayName("Inventory Service Implementation Test - ✅ Should Delete Inventory With Matching Product-Id")
    void shouldDeleteInventory() {
        // Arrange
        when(inventoryRepo.findByProductId(productId)).thenReturn(Optional.of(savedInventory));
        doNothing().when(inventoryRepo).delete(savedInventory);
        // Act
        inventoryService.deleteStockEntry(productId);
        // Assert
        // Verify
        verify(inventoryRepo, times(1)).findByProductId(productId);
        verify(inventoryRepo, times(1)).delete(savedInventory);
    }
}