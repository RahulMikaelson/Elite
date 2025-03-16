package com.elite.service.order.controller;

import com.elite.service.order.dto.*;
import com.elite.service.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsDTO> getOrderDetails(@PathVariable UUID orderId) {
        log.info("Order Controller - Getting details of the order with id : {}", orderId);
        return new ResponseEntity<>(orderService.getOrderDetails(orderId), HttpStatus.OK);
    }

    @PostMapping("/place-order")
    public ResponseEntity<OrderResponseDTO> placeOrder(@AuthenticationPrincipal Jwt jwt, @RequestBody OrderRequestDTO orderRequest) {
        orderRequest.setCustomerId(UUID.fromString(jwt.getClaim("sub")));
        log.info("Order Controller - Placing order : {} for the user with id : {}", orderRequest, jwt.getClaim("sub"));
        return new ResponseEntity<>(orderService.placeOrder(orderRequest),HttpStatus.CREATED);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<OrderResponseDTO>> getCustomerOrders(@AuthenticationPrincipal Jwt jwt) {
        UUID customerId = UUID.fromString(jwt.getClaim("sub"));
        log.info("Order Controller - Getting customer orders with id : {}", customerId);
        return new ResponseEntity<>(orderService.getCustomerOrders(customerId),HttpStatus.OK);
    }

    @PutMapping("/update/{orderId}/status")
    public ResponseEntity<OrderStatusDTO> updateOrderStatus(@PathVariable UUID orderId,@RequestBody UpdateOrderStatusDTO updateOrderStatusDTO) {
        log.info("Order Controller - Updating order status with id : {} to {}",orderId, updateOrderStatusDTO.getStatus());
        return  new ResponseEntity<>(orderService.updateOrderStatus(orderId,updateOrderStatusDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<OrderDeleteResponse> deleteOrder(@PathVariable UUID orderId) {
        log.info("Order Controller - Deleting order with id : {}", orderId);
        return new ResponseEntity<>(orderService.deleteOrder(orderId),HttpStatus.OK);
    }

}
