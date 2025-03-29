package com.elite.service.inventory.service;

import com.elite.service.inventory.dto.InventoryListResponseDTO;
import com.elite.service.inventory.dto.InventoryRequestDTO;
import com.elite.service.inventory.dto.InventoryResponseDTO;
import com.elite.service.inventory.dto.StockUpdateRequestDTO;

public interface InventoryService {
    InventoryResponseDTO getProductInventory(String productId);

    InventoryListResponseDTO fetchAllInventories();

    InventoryResponseDTO addNewStockEntry(InventoryRequestDTO inventoryRequestDTO);

    InventoryResponseDTO updateStockEntry(String productId, StockUpdateRequestDTO stockUpdateRequestDTO);

    void deleteStockEntry(String productId);
}
