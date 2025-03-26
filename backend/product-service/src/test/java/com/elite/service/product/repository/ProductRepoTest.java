package com.elite.service.product.repository;

import com.elite.service.product.document.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ProductRepoTest {
    // Mock Products
    List<Product> productList;
    List<Product> categoryProductList;
    //Mock Page
    Pageable pageable;
    Page<Product> productPage;

    @Mock
    ProductRepo productRepo;

    @BeforeAll
    static void initAll() {
        log.info("Product Repository Test - Initializing resources before all tests and Starting Testing");
    }

    @AfterAll
    static void cleanUpAll() {
        log.info("Product Repository Test - Cleaning up all tests");
    }


    @BeforeEach
    void setUp() {
        productList = List.of(
                new Product("P001", "Laptop", "High-performance laptop", "Electronics", 75000.0, 10),
                new Product("P002", "Smartphone", "Latest Android smartphone", "Electronics", 45000.0, 20),
                new Product("P003", "Headphones", "Wireless noise-canceling headphones", "Accessories", 5000.0, 15),
                new Product("P004", "Smartwatch", "Fitness tracking smartwatch", "Wearables", 12000.0, 25),
                new Product("P005", "Bluetooth Speaker", "Portable Bluetooth speaker", "Audio", 3000.0, 30),
                new Product("P006", "Gaming Mouse", "Ergonomic gaming mouse", "Gaming", 2500.0, 40),
                new Product("P007", "Mechanical Keyboard", "RGB mechanical keyboard", "Gaming", 7000.0, 10),
                new Product("P008", "External SSD", "1TB high-speed external SSD", "Storage", 12000.0, 5)
        );
        categoryProductList = List.of(
                new Product("P005", "Bluetooth Speaker", "Portable Bluetooth speaker", "Audio", 3000.0, 30)
        );
        pageable = PageRequest.of(0, 10);
        productPage = new PageImpl<>(categoryProductList, pageable, 1);
    }

    @Test
    @DisplayName("Product Repository Test - ✅ Should Return All Product of Category `Audio`")
    void shouldReturnAllProductOfCategoryAudio() {
        // Arrange
        when(productRepo.findByProductCategory("Audio",pageable)).thenReturn(productPage);
        // Act
        Page<Product> audioCategory = productRepo.findByProductCategory("Audio", pageable);
        // Assert
        assertNotNull(audioCategory,"Expected non-null Page<Product> response");
        assertFalse(audioCategory.getContent().isEmpty(),"Expected at least one product in the result");
        assertTrue(audioCategory.getContent().containsAll(categoryProductList),"Expected products do not match");
        assertEquals(categoryProductList.size(), audioCategory.getTotalElements(),"Total elements mismatch");
        // Verify
        verify(productRepo, times(1)).findByProductCategory("Audio", pageable);
    }
}