package com.elite.service.inventory.controller;

import com.elite.service.inventory.dto.InventoryListResponseDTO;
import com.elite.service.inventory.dto.InventoryRequestDTO;
import com.elite.service.inventory.dto.InventoryResponseDTO;
import com.elite.service.inventory.dto.StockUpdateRequestDTO;
import com.elite.service.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> getProductInventory(@PathVariable String productId) {
        log.info("Inventory Controller - Getting product inventory for productId: {}", productId);
        return new ResponseEntity<>(inventoryService.getProductInventory(productId), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<InventoryListResponseDTO> fetchAllInventories(){
        log.info("Inventory Controller - Fetching all inventories");
        return new ResponseEntity<>(inventoryService.fetchAllInventories(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> addNewStockEntry(@RequestBody InventoryRequestDTO inventoryRequestDTO) {
        log.info("Inventory Controller - Adding new stock entry {}:",inventoryRequestDTO);
        return new ResponseEntity<>(inventoryService.addNewStockEntry(inventoryRequestDTO),HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> updateStockEntry(@PathVariable String productId, @RequestBody StockUpdateRequestDTO stockUpdateRequestDTO) {
        log.info("Inventory Controller - Updating stock entry for inventory with product-id {}:",productId);
        return new ResponseEntity<>(inventoryService.updateStockEntry(productId,stockUpdateRequestDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteStockEntry(@PathVariable("productId") String productId) {
        log.info("Inventory Controller - Deleting stock entry for product-id {}:",productId);
        inventoryService.deleteStockEntry(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
