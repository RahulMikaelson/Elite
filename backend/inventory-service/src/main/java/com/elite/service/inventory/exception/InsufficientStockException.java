package com.elite.service.inventory.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InsufficientStockException extends RuntimeException {
    private String errorCode;
    private String productSKU;

    public InsufficientStockException(String message, String errorCode,String productSKU) {
        super(message);
        this.errorCode = errorCode;
        this.productSKU = productSKU;
    }
}
