package com.elite.service.order.service;

import com.elite.service.order.dto.*;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDetailsDTO getOrderDetails(UUID orderId);

    OrderResponseDTO placeOrder(OrderRequestDTO orderRequest);

    List<OrderResponseDTO> getCustomerOrders(UUID customerId);

    OrderStatusDTO updateOrderStatus(UUID orderId, UpdateOrderStatusDTO updateOrderStatusDTO);

    OrderDeleteResponse deleteOrder(UUID orderId);
}
