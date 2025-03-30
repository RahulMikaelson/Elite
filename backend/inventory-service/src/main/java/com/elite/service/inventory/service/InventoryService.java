package com.elite.service.inventory.service;

import com.elite.service.inventory.dto.*;

public interface InventoryService {
    InventoryResponseDTO getProductInventory(String productId);

    InventoryListResponseDTO fetchAllInventories();

    InventoryResponseDTO addNewStockEntry(InventoryRequestDTO inventoryRequestDTO);

    InventoryResponseDTO updateStockEntry(String productId, StockUpdateRequestDTO stockUpdateRequestDTO);

    void deleteStockEntry(String productId);

    StockReductionResponseDTO reduceInventoryStock(String productId, StockUpdateRequestDTO stockUpdateRequestDTO);
}
