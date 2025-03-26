package com.elite.service.order.service;

import com.elite.service.order.dto.*;
import com.elite.service.order.entity.Order;
import com.elite.service.order.enums.OrderStatus;
import com.elite.service.order.exception.OrderNotFoundException;
import com.elite.service.order.mapper.OrderMapper;
import com.elite.service.order.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;

    private Order getOrderById(UUID orderId) {
        log.info("Order Controller - Getting order with id : {}", orderId);
        return orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found with id : " + orderId, "ORDER_NOT_FOUND"));
    }

    @Override
    public OrderDetailsDTO getOrderDetails(UUID orderId) {
        log.info("Order Service - Getting details of the order with id : {}", orderId);
        Order order = getOrderById(orderId);
        log.info("Order Service - Order details of the order with id : {} are {}", orderId, order);
        return orderMapper.orderToOrderDetailsDTO(order);
    }

    @Override
    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequest) {
        Order order = orderMapper.OrderRequestDTOToOrder(orderRequest);
        double sum = orderRequest.getOrderItems().stream().mapToDouble(item -> item.getQuantity() * item.getPricePerUnit()).sum();
        order.getOrderItems().forEach(item -> item.setTotalPrice(item.getQuantity() * item.getPricePerUnit()));
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(sum);
        log.info("Order Service - Saving Order : {}", order);
        Order savedOrder = orderRepo.save(order);
        log.info("Order Service - Order saved : {}", savedOrder);
        return orderMapper.orderToOrderResponseDTO(savedOrder);
    }

    @Override
    public List<OrderResponseDTO> getCustomerOrders(UUID customerId) {
        log.info("Order Service - Getting customer orders with id : {}", customerId);
        return orderRepo.findByCustomerId(customerId).stream().map(orderMapper::orderToOrderResponseDTO).toList();
    }

    @Override
    public OrderStatusDTO updateOrderStatus(UUID orderId, UpdateOrderStatusDTO updateOrderStatusDTO) {
        log.info("Order Service - Updating order status with id : {} to {}", orderId, updateOrderStatusDTO.getStatus());
        Order order = getOrderById(orderId);
        order.setStatus(updateOrderStatusDTO.getStatus());
        OrderStatusDTO updatedOrder = orderMapper.orderToOrderStatusDTO(orderRepo.save(order));
        log.info("Order Service - Order status updated : {}", updatedOrder);
        return updatedOrder;
    }

    @Override
    public OrderDeleteResponse deleteOrder(UUID orderId) {
        Order order = getOrderById(orderId);
        log.info("Order Service - Deleting order with id : {}", orderId);
        orderRepo.delete(order);
        return new OrderDeleteResponse("Order "+orderId+" has been deleted Successfully.");
    }
}
