package com.elite.service.inventory.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryNotFoundException extends RuntimeException {
    private String errorCode;

    public InventoryNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
