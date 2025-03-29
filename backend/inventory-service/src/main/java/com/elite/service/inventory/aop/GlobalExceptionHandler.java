package com.elite.service.inventory.aop;

import com.elite.service.inventory.dto.ExceptionResponseDTO;
import com.elite.service.inventory.exception.InventoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> OrderNotFoundExceptionHandler(InventoryNotFoundException exception) {
        return new ResponseEntity<>(new ExceptionResponseDTO(exception.getErrorCode(), exception.getMessage(), new Date()), HttpStatus.NOT_FOUND);
    }
}
