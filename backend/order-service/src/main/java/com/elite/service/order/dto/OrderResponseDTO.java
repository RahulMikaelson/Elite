package com.elite.service.order.dto;

import com.elite.service.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    private UUID orderId;
    private UUID customerId;
    private Double totalAmount;
    private OrderStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
