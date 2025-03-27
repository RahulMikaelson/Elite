package com.elite.service.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private String productId;
    private String productName;
    private String productDescription;
    private String productCategory;
    private Double productPrice;
    private String sku;
    private String brandName;
    private Instant createdAt;
    private Instant updatedAt;
}
