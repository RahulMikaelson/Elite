package com.elite.service.inventory.service;

import com.elite.service.inventory.dto.*;
import com.elite.service.inventory.entity.Inventory;
import com.elite.service.inventory.exception.InsufficientStockException;
import com.elite.service.inventory.exception.InventoryNotFoundException;
import com.elite.service.inventory.mapper.InventoryMapper;
import com.elite.service.inventory.repository.InventoryRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepo inventoryRepo;

    private final InventoryMapper inventoryMapper;

    private Inventory getInventoryByProductId(String productId) {
        log.info("Inventory Service - Getting product inventory for productId: {}", productId);
        return inventoryRepo.findByProductId(productId).orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product-id: " + productId, "INVENTORY_NOT_FOUND"));
    }

    @Override
    public InventoryResponseDTO getProductInventory(String productId) {
        log.info("Inventory Service - Getting inventory with productId: {}", productId);
        return inventoryMapper.inventoryToInventoryResponseDTO(getInventoryByProductId(productId));
    }

    @Override
    public InventoryListResponseDTO fetchAllInventories() {
        log.info("Inventory Service - Fetching all inventories");
        List<InventoryResponseDTO> list = inventoryRepo.findAll().stream().map(inventoryMapper::inventoryToInventoryResponseDTO).toList();
        log.info("Inventory Service - Fetched all inventories");
        return new InventoryListResponseDTO(list);
    }

    @Override
    public InventoryResponseDTO addNewStockEntry(InventoryRequestDTO inventoryRequestDTO) {
        log.info("Inventory Service - Adding new stock entry");
        Inventory inventory = inventoryMapper.inventoryRequestDTOToInventory(inventoryRequestDTO);
        log.info("Inventory Service - Adding new stock entry : {}", inventory);
        inventory = inventoryRepo.save(inventory);
        log.info("Inventory Service - Added new stock entry : {}", inventory);
        return inventoryMapper.inventoryToInventoryResponseDTO(inventory);
    }

    @Override
    public InventoryResponseDTO updateStockEntry(String productId, StockUpdateRequestDTO stockUpdateRequestDTO) {
        log.info("Inventory Service - Updating stock entry for product-id {}:", productId);
        Inventory inventory = getInventoryByProductId(productId);
        log.info("Inventory Service - Updating stock entry for product-id {} from {} to {}:", productId, inventory.getStock(), inventory.getStock() + stockUpdateRequestDTO.getQuantity());
        inventory.setStock(inventory.getStock() + stockUpdateRequestDTO.getQuantity());
        inventory = inventoryRepo.save(inventory);
        log.info("Inventory Service - Updated inventory : {}", inventory);
        return inventoryMapper.inventoryToInventoryResponseDTO(inventory);
    }

    @Override
    public StockReductionResponseDTO reduceInventoryStock(String productId, StockUpdateRequestDTO stockUpdateRequestDTO) throws InsufficientStockException {
        log.info("Inventory Service - Getting inventory for product-id {}:", productId);
        Inventory inventory = getInventoryByProductId(productId);
        if (inventory.getStock() < stockUpdateRequestDTO.getQuantity()) {
            log.info("Inventory Service - Insufficient inventory for product-id {},: Only {} units left, but units {} were requested.", productId,inventory.getStock(),stockUpdateRequestDTO.getQuantity());
            throw new InsufficientStockException("Insufficient stock available. Only "+inventory.getStock()+" units left, but "+ stockUpdateRequestDTO.getQuantity()+" units were requested.","INSUFFICIENT_STOCK_AVAILABLE", inventory.getSku());
        }
        log.info("Inventory Service - Reducing the stock entry for product-id {} by {}:", productId, stockUpdateRequestDTO.getQuantity());
        inventory.setStock(inventory.getStock() - stockUpdateRequestDTO.getQuantity());
        inventory = inventoryRepo.save(inventory);
        return StockReductionResponseDTO.builder()
                .message("Stock reduced successfully")
                .productId(inventory.getProductId())
                .remainingStock(inventory.getStock())
                .sku(inventory.getSku())
                .build();
    }

    @Override
    public void deleteStockEntry(String productId) {
        log.info("Inventory Service - Deleting stock entry for product-id {}:", productId);
        Inventory inventory = getInventoryByProductId(productId);
        log.info("Inventory Service - Deleting Inventory {}", inventory);
        inventoryRepo.delete(inventory);
    }
}
