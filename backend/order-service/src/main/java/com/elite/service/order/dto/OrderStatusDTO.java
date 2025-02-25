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
public class OrderStatusDTO {
    private UUID orderId;
    private OrderStatus status;
    private Instant updatedAt;
}
