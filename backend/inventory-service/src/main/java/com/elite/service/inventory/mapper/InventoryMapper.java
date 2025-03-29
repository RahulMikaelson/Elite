package com.elite.service.inventory.mapper;

import com.elite.service.inventory.dto.InventoryRequestDTO;
import com.elite.service.inventory.dto.InventoryResponseDTO;
import com.elite.service.inventory.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InventoryMapper {

    Inventory inventoryRequestDTOToInventory(InventoryRequestDTO inventoryRequestDTO);
    InventoryResponseDTO inventoryToInventoryResponseDTO(Inventory inventory);
}
