package com.elite.service.order.dto;


import com.elite.service.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailsDTO {


    private UUID orderId;
    private UUID customerId;
    private Double totalAmount;
    private OrderStatus status;
    private List<OrderItemDTO> orderItems;
    private Instant createdAt;
    private Instant updatedAt;
}
