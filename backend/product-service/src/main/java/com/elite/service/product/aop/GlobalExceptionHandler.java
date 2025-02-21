package com.elite.service.product.aop;

import com.elite.service.product.dto.ExceptionResponseDTO;
import com.elite.service.product.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> productNotFoundExceptionHandler(ProductNotFoundException exception) {
        return new ResponseEntity<>( new ExceptionResponseDTO(exception.getExceptionCode(), exception.getMessage(), new Date()), HttpStatus.NOT_FOUND);
    }
}
