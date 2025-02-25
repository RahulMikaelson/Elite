package com.elite.service.order.repository;

import com.elite.service.order.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepo extends JpaRepository<OrderItems, UUID> {
}
