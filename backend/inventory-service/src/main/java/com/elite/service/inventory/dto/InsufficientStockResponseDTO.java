package com.elite.service.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InsufficientStockResponseDTO {
    private String exceptionMessage;
    private String exceptionCode;
    private String productSKU;
    private Date timeStamp;
}
