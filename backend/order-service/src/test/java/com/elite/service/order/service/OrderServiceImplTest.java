package com.elite.service.order.service;

import com.elite.service.order.dto.*;
import com.elite.service.order.entity.Order;
import com.elite.service.order.entity.OrderItems;
import com.elite.service.order.enums.OrderStatus;
import com.elite.service.order.exception.OrderNotFoundException;
import com.elite.service.order.mapper.OrderMapper;
import com.elite.service.order.repository.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    // Order Request
    OrderRequestDTO orderRequestDTO;
    List<Item> items;

    // Creating Order
    Order order;
    List<OrderItems> orderItems;

    // Saved Order and Order Response
    Order savedOrder;
    OrderResponseDTO orderResponseDTO;

    // Get Order
    List<Order> orders;
    List<OrderResponseDTO> customerOrdersResponseDTOS;
    OrderDetailsDTO orderDetailsDTO;
    List<OrderItemDTO> orderItemsDTO;

    // Update Order
    UpdateOrderStatusDTO updateOrderStatusDTO;
    Order updatedOrder;
    OrderStatusDTO orderStatusDTO;

    // Delete Order Response
    OrderDeleteResponse orderDeleteResponse;


    @Mock
    private OrderRepo orderRepo;
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeAll
    static void initAll() {
        log.info("Order Service Implementation Test - Initializing resources before all tests and Starting Testing");
    }

    @AfterAll
    static void cleanUpAll() {
        log.info("Order Service Implementation Test - Cleaning up all tests");
    }

    @BeforeEach
    void setUp() {
        /* Order Request */
        // List of Items in Order Request
        items = List.of(
                new Item("P001", "Laptop", 2, 50000.0),
                new Item("P002", "Smartphone", 1, 30000.0),
                new Item("P003", "Headphones", 3, 2000.0)
        );
        orderRequestDTO = new OrderRequestDTO(UUID.fromString("6772f5bd-bcbf-4dab-95a0-f29b822c4c0c"),items);


        /* Creating Order*/
        // Order Items
        orderItems = List.of(
                new OrderItems(UUID.fromString("04ac6677-9fbc-4f0b-9f11-1bf45a62ee5a"), "P001", "Laptop", 2, 50000.0, 100000.0),
                new OrderItems(UUID.fromString("85de4b5b-d136-46ee-9d6e-f95c62692190"), "P002", "Smartphone", 1, 30000.0, 30000.0),
                new OrderItems(UUID.fromString("759226c4-a919-4d1b-b1ee-c8eafd7469f1"), "P003", "Headphones", 3, 2000.0, 6000.0)
        );
        order = new Order(null, UUID.fromString("6772f5bd-bcbf-4dab-95a0-f29b822c4c0c"), 100.32, OrderStatus.PENDING, orderItems, Instant.now(), Instant.now());

        /* Save Order and Order Response*/
        savedOrder = new Order(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"), UUID.fromString("6772f5bd-bcbf-4dab-95a0-f29b822c4c0c"), 136000.0, OrderStatus.PENDING, orderItems, Instant.now(), Instant.now());
        orderResponseDTO = new OrderResponseDTO(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"), UUID.fromString("6772f5bd-bcbf-4dab-95a0-f29b822c4c0c"), 136000.0, OrderStatus.PENDING, Instant.now(), Instant.now());

        /*Get Order Details*/
        //  OrderItems DTO List
        orderItemsDTO =  List.of(
                new OrderItemDTO(UUID.fromString("04ac6677-9fbc-4f0b-9f11-1bf45a62ee5a"), "P001", "Laptop", 2, 50000.0, 100000.0),
                new OrderItemDTO(UUID.fromString("85de4b5b-d136-46ee-9d6e-f95c62692190"), "P002", "Smartphone", 1, 30000.0, 30000.0),
                new OrderItemDTO(UUID.fromString("759226c4-a919-4d1b-b1ee-c8eafd7469f1"), "P003", "Headphones", 3, 2000.0, 6000.0)
        );
        orderDetailsDTO = new OrderDetailsDTO(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"), UUID.fromString("6772f5bd-bcbf-4dab-95a0-f29b822c4c0c"), 136000.0, OrderStatus.PENDING, orderItemsDTO, Instant.now(), Instant.now());
        orders = List.of(savedOrder);
        customerOrdersResponseDTOS = List.of(new OrderResponseDTO(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"), UUID.fromString("6772f5bd-bcbf-4dab-95a0-f29b822c4c0c"), 136000.0, OrderStatus.PENDING, Instant.now(), Instant.now()));

        /* Update Order */
        updateOrderStatusDTO = new UpdateOrderStatusDTO(OrderStatus.CONFIRMED);
        updatedOrder = new Order(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"), UUID.fromString("6772f5bd-bcbf-4dab-95a0-f29b822c4c0c"), 136000.0, OrderStatus.CONFIRMED, orderItems, Instant.now(), Instant.now());
        orderStatusDTO = new OrderStatusDTO(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"),OrderStatus.CONFIRMED,Instant.now());

        /* Order Delete Response*/
        orderDeleteResponse = new OrderDeleteResponse("Order 7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf has been deleted Successfully.");
    }



    @Test
    @DisplayName("Order Service Implementation Test - ✅ Should Return Order Details DTO When Order Is Found With Id(UUID)")
    void shouldReturnOrderDetailsDTOWhenOrderIsFoundWithId() {
        // Arrange
        when(orderRepo.findById(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"))).thenReturn(Optional.of(savedOrder));
        when(orderMapper.orderToOrderDetailsDTO(savedOrder)).thenReturn(orderDetailsDTO);
        // Act
        OrderDetailsDTO orderDetails = orderService.getOrderDetails(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"));
        // Assert
        assertEquals(orderDetailsDTO.getOrderId(), orderDetails.getOrderId());
        assertEquals(orderDetailsDTO.getStatus(), orderDetails.getStatus());
        assertEquals(orderDetailsDTO.getTotalAmount(), orderDetails.getTotalAmount());
        assertEquals(orderDetailsDTO.getCreatedAt(), orderDetails.getCreatedAt());
        assertEquals(orderDetailsDTO.getUpdatedAt(), orderDetails.getUpdatedAt());
        // Verify
        verify(orderRepo, times(1)).findById(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"));
        verify(orderMapper, times(1)).orderToOrderDetailsDTO(savedOrder);
    }

    @Test
    @DisplayName("Order Service Implementation Test - ❌ Should Throw Order Not Found Exception when Order Is Not Found With Id(UUID)")
    void shouldThrowOrderNotFoundExceptionWhenOrderIsNotFoundWithId() {
        // Arrange
        when(orderRepo.findById(UUID.fromString("214147a3-2984-4b86-912d-70ca619c33f5"))).thenThrow(new OrderNotFoundException("Order not found with id : 214147a3-2984-4b86-912d-70ca619c33f5","ORDER_NOT_FOUND"));
        // Act and Assert
        OrderNotFoundException orderException = assertThrows(OrderNotFoundException.class, () -> orderRepo.findById(UUID.fromString("214147a3-2984-4b86-912d-70ca619c33f5")));
        assertEquals("Order not found with id : 214147a3-2984-4b86-912d-70ca619c33f5" , orderException.getMessage());
        // Verify
        verify(orderRepo, times(1)).findById(UUID.fromString("214147a3-2984-4b86-912d-70ca619c33f5"));
    }
    @Test
    @DisplayName("Order Service Implementation Test - ✅ Successfully Place the Order")
    void placeOrder() {
        // Arrange
        when(orderMapper.OrderRequestDTOToOrder(orderRequestDTO)).thenReturn(order);
        when(orderRepo.save(order)).thenReturn(savedOrder);
        when(orderMapper.orderToOrderResponseDTO(savedOrder)).thenReturn(orderResponseDTO);
        // Act
        OrderResponseDTO savedOrderResponseDTO = orderService.placeOrder(orderRequestDTO);
        // Assert
        assertEquals(orderResponseDTO.getOrderId(), savedOrderResponseDTO.getOrderId());
        assertEquals(orderResponseDTO.getCustomerId(), savedOrderResponseDTO.getCustomerId());
        assertEquals(orderResponseDTO.getStatus(), savedOrderResponseDTO.getStatus());
        assertEquals(orderResponseDTO.getTotalAmount(), savedOrderResponseDTO.getTotalAmount());
        assertEquals(orderResponseDTO.getCreatedAt(), savedOrderResponseDTO.getCreatedAt());
        assertEquals(orderResponseDTO.getUpdatedAt(), savedOrderResponseDTO.getUpdatedAt());
        // Verify
        verify(orderMapper,times(1)).OrderRequestDTOToOrder(orderRequestDTO);
        verify(orderRepo, times(1)).save(order);
        verify(orderMapper,times(1)).orderToOrderResponseDTO(savedOrder);
    }

    @Test
    @DisplayName("Order Service Implementation Test - ✅ Get Customer Order using Customer Id(UUID)")
    void shouldGetCustomerOrderUsingCustomerId() {
        // Arrange
        when(orderRepo.findByCustomerId(UUID.fromString("6772f5bd-bcbf-4dab-95a0-f29b822c4c0c"))).thenReturn(orders);
        when(orderMapper.orderToOrderResponseDTO(any())).thenReturn(orderResponseDTO);
        // Act
        List<OrderResponseDTO> listOfCustomerOrders = orderService.getCustomerOrders(UUID.fromString("6772f5bd-bcbf-4dab-95a0-f29b822c4c0c"));
        // Assert
        assertEquals(orders.size(), listOfCustomerOrders.size());
        // Verify
        verify(orderRepo, times(1)).findByCustomerId(UUID.fromString("6772f5bd-bcbf-4dab-95a0-f29b822c4c0c"));
        verify(orderMapper,times(listOfCustomerOrders.size())).orderToOrderResponseDTO(any());
    }

    @Test
    @DisplayName("Order Service Implementation Test - ✅ Update the Order when the Order is found with Id(UUID)")
    void shouldUpdateOrderStatus() {
        // Arrange
        when(orderRepo.findById(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"))).thenReturn(Optional.of(savedOrder));
        when(orderRepo.save(updatedOrder)).thenReturn(updatedOrder);
        when(orderMapper.orderToOrderStatusDTO(updatedOrder)).thenReturn(orderStatusDTO);
        // Act
        OrderStatusDTO updatedOrderStatusDTO = orderService.updateOrderStatus(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"), updateOrderStatusDTO);
        // Assert
        assertEquals(OrderStatus.CONFIRMED, updatedOrderStatusDTO.getStatus());
        assertEquals(updatedOrderStatusDTO.getStatus(), updatedOrder.getStatus());
        assertEquals(updatedOrderStatusDTO.getOrderId(), updatedOrder.getOrderId());
        assertEquals(updatedOrderStatusDTO.getUpdatedAt(), updatedOrder.getUpdatedAt());
        // verify
        verify(orderRepo,times(1)).findById(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"));
        verify(orderRepo, times(1)).save(updatedOrder);
        verify(orderMapper, times(1)).orderToOrderStatusDTO(updatedOrder);
    }

    @Test
    @DisplayName("Order Service Implementation Test - ✅ Delete Order when order is found with Id(UUID)")
    void shouldDeleteOrderWhenOrderIsFoundWithId() {
        // Arrange
        when(orderRepo.findById(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"))).thenReturn(Optional.of(savedOrder));
        doNothing().when(orderRepo).delete(savedOrder);
        // Act
        OrderDeleteResponse deletedOrderResponse = orderService.deleteOrder(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"));
        // Assert
        assertEquals(deletedOrderResponse.getMessage(), orderDeleteResponse.getMessage());
        // Verify
        verify(orderRepo,times(1)).findById(UUID.fromString("7ba4afe1-5aab-4170-9e22-1c3fdd7f47cf"));
        verify(orderRepo,times(1)).delete(savedOrder);
    }
}