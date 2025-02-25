package com.elite.service.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {

    private UUID id;
    private String productId;
    private String productName;
    private Integer quantity;
    private Double pricePerUnit;
    private Double totalPrice;
}
