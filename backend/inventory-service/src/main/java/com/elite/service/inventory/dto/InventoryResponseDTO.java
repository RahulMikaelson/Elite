package com.elite.service.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponseDTO {
    private UUID id;
    private String productId;
    private String sku;
    private Integer stock;
    private Instant createdAt;
    private Instant updatedAt;
}
