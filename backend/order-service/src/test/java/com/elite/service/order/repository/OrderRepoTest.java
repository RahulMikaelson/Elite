package com.elite.service.order.repository;

import com.elite.service.order.dto.*;
import com.elite.service.order.entity.Order;
import com.elite.service.order.entity.OrderItems;
import com.elite.service.order.enums.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class OrderRepoTest {
    // Order Mock
    Order savedOrder;
    List<OrderItems> orderItems;
    List<Order> orders;

    // Customer Mock
    UUID customerId;

    @Mock
    private OrderRepo orderRepo;

    @BeforeAll
    static void initAll() {
        log.info("Order Repository Test - Initializing resources before all tests and Starting Testing");
    }

    @AfterAll
    static void cleanUpAll() {
        log.info("Order Repository Test - Cleaning up all tests");
    }

    @BeforeEach
    void setUp() {
        /* Creating Customer Order*/
        customerId = UUID.randomUUID();
        orderItems = List.of(
                new OrderItems(UUID.fromString("04ac6677-9fbc-4f0b-9f11-1bf45a62ee5a"), "P001", "Laptop", 2, 50000.0, 100000.0),
                new OrderItems(UUID.fromString("85de4b5b-d136-46ee-9d6e-f95c62692190"), "P002", "Smartphone", 1, 30000.0, 30000.0),
                new OrderItems(UUID.fromString("759226c4-a919-4d1b-b1ee-c8eafd7469f1"), "P003", "Headphones", 3, 2000.0, 6000.0)
        );
        savedOrder = new Order(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"), customerId, 136000.0, OrderStatus.PENDING, orderItems, Instant.now(), Instant.now());
        orders = List.of(savedOrder);


    }

    @Test
    @DisplayName("Order Repository Test - ✅ Should Return All Order of The Customer")
    void shouldFindAllOrderOfTheCustomerByCustomerId() {
        // Arrange
        when(orderRepo.findByCustomerId(customerId)).thenReturn(orders);
        // Act
        List<Order> customerOrders = orderRepo.findByCustomerId(customerId);
        // Assert
        assertNotNull(customerOrders, "Expected non-null order list, but got null");
        assertEquals(orders.size(), customerOrders.size(), "Mismatch in the number of retrieved orders");
        assertEquals(
                orders.getFirst().getCustomerId(),
                customerOrders.getFirst().getCustomerId(),
                "Customer ID of retrieved order does not match expected value"
        );
        // Verify
        verify(orderRepo,times(1)).findByCustomerId(customerId);
    }

    @Test
    @DisplayName("Order Repository Test - ✅ Should Return Empty List When Customer With No Orders")
    void shouldReturnEmptyListWhenUserHaveNoOrders() {
        UUID randomUUID = UUID.randomUUID();
        // Arrange
        when(orderRepo.findByCustomerId(randomUUID)).thenReturn(List.of());
        // Act
        List<Order> customerOrders = orderRepo.findByCustomerId(randomUUID);
        // Assert
        assertEquals(0, customerOrders.size(), "Expected non-null order list, but got null");
        // Verify
        verify(orderRepo,times(1)).findByCustomerId(randomUUID);
    }


}