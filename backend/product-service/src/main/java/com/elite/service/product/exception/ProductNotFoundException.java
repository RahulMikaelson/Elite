package com.elite.service.product.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductNotFoundException extends RuntimeException {
    private String exceptionCode;

    public ProductNotFoundException(String message, String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    }
}
