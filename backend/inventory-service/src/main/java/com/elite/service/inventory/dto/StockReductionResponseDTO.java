package com.elite.service.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockReductionResponseDTO {
    private String message;
    private String productId;
    private String sku;
    private int remainingStock;
}
