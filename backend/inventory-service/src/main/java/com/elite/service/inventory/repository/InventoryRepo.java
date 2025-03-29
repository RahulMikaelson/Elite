package com.elite.service.inventory.repository;

import com.elite.service.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, UUID> {

    Optional<Inventory> findByProductId(String productId);
}
