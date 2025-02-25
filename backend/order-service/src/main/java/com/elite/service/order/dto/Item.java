package com.elite.service.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    private String productId;
    private String productName;
    private Integer quantity;
    private Double pricePerUnit;
}
