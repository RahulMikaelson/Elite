package com.elite.service.order.repository;

import com.elite.service.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {

    List<Order> findByCustomerId(UUID customerId);
}
