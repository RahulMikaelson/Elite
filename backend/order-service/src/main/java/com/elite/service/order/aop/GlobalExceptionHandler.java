package com.elite.service.order.aop;

import com.elite.service.order.dto.ExceptionResponseDTO;
import com.elite.service.order.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> OrderNotFoundExceptionHandler(OrderNotFoundException exception) {
        return new ResponseEntity<>(new ExceptionResponseDTO(exception.getErrorCode(), exception.getMessage(), new Date()), HttpStatus.NOT_FOUND);
    }
}
