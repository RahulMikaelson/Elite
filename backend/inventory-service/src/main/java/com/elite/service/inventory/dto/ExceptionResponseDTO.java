package com.elite.service.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDTO {
    private String exceptionCode;
    private String exceptionMessage;
    private Date timeStamp;
}
