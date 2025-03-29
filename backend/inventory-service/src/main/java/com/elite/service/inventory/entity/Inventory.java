package com.elite.service.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventories")
@EntityListeners(AuditingEntityListener.class)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "product_id",unique = true, nullable = false)
    private String productId;
    @Column(name = "sku",unique = true, nullable = false)
    private String sku;
    @Column(name = "stock",nullable = false)
    private Integer stock;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
