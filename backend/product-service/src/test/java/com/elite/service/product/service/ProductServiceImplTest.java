package com.elite.service.product.service;

import com.elite.service.product.document.Product;
import com.elite.service.product.dto.PaginatedResponse;
import com.elite.service.product.dto.ProductRequestDTO;
import com.elite.service.product.dto.ProductResponseDTO;
import com.elite.service.product.exception.ProductNotFoundException;
import com.elite.service.product.mapper.ProductMapper;
import com.elite.service.product.repository.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepo productRepo;
    @Mock
    ProductMapper productMapper;
    @InjectMocks
    ProductServiceImpl productService;

    private Product product;
    private ProductRequestDTO productRequestDTO;
    private ProductResponseDTO productResponseDTO;

    @BeforeAll
    static void intiAll() {
        log.info("Product Service Implementation Test - Initializing resources before all tests and Starting Testing");
    }

    @AfterAll
    static void cleanUpAll() {
        log.info("Product Service Implementation Test - Completed All Test");
    }

    @BeforeEach
    void setUp() {
        product = new Product("75bcb335-f59f-4f27-8a16-39cd95a6fd4e", "product-1", "This is description for product-1", "Test-Category", 15.23, "elite", "p-1",Instant.now(),Instant.now());
        productResponseDTO = new ProductResponseDTO("75bcb335-f59f-4f27-8a16-39cd95a6fd4e", "product-1", "This is description for product-1", "Test-Category", 15.23, "elite", "p-1",Instant.now(),Instant.now());
        productRequestDTO = new ProductRequestDTO("product-1", "This is description for product-1", "Test-Category", 15.23,"p-1" ,"elite");
    }


    @Test
    @DisplayName("Product Service Implementation Test - ✅ Should return ProductResponseDTO when product ID is found")
    void shouldReturnProductResponseDTOWhenProductIsValid() {
        // Arrange
        when(productRepo.findById("75bcb335-f59f-4f27-8a16-39cd95a6fd4e")).thenReturn(Optional.of(product));
        when(productMapper.toProductResponseDTO(product)).thenReturn(productResponseDTO);
        // Act
        ProductResponseDTO dto = productService.getProductById("75bcb335-f59f-4f27-8a16-39cd95a6fd4e");
        // Assert
        assertEquals(productResponseDTO.getProductId(), dto.getProductId());
        assertEquals(productResponseDTO.getProductName(), dto.getProductName());
        assertEquals(productResponseDTO.getProductDescription(), dto.getProductDescription());
        assertEquals(productResponseDTO.getProductCategory(), dto.getProductCategory());
        assertEquals(productResponseDTO.getProductPrice(), dto.getProductPrice());
        assertEquals(productResponseDTO.getSku(), dto.getSku());
        assertEquals(productResponseDTO.getBrandName(), dto.getBrandName());
        // Verify
        verify(productRepo,times(1)).findById("75bcb335-f59f-4f27-8a16-39cd95a6fd4e");
        verify(productMapper,times(1)).toProductResponseDTO(product);
    }

    @Test
    @DisplayName("Product Service Implementation Test - ❌ Should throw ProductNotFoundException when product ID is not found")
    void shouldThrowExceptionWhenProductIsNotValid() {
        // Arrange
        when(productRepo.findById("Test")).thenReturn(Optional.empty());

        // Act & Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById("Test"));

        assertEquals("Product Not Found with Id :Test", exception.getMessage());

        // Verify
        verify(productRepo, times(1)).findById("Test");
    }

    @Test
    @DisplayName("Product Service Implementation Test - ✅ Should successfully add a product")
    void shouldAddProduct() {
        // Arrange
        when(productRepo.save(product)).thenReturn(product);
        when(productMapper.toProduct(productRequestDTO)).thenReturn(product);
        when(productMapper.toProductResponseDTO(product)).thenReturn(productResponseDTO);
        // Act
        ProductResponseDTO dto = productService.addProduct(productRequestDTO);
        // Assert
        assertNotNull(dto);
        assertEquals(productResponseDTO.getProductId(), dto.getProductId());
        assertEquals(productResponseDTO.getProductName(), dto.getProductName());
        assertEquals(productResponseDTO.getProductDescription(), dto.getProductDescription());
        assertEquals(productResponseDTO.getProductCategory(), dto.getProductCategory());
        assertEquals(productResponseDTO.getProductPrice(), dto.getProductPrice());
        assertInstanceOf(ProductResponseDTO.class, dto);
        // Verify
        verify(productRepo,times(1)).save(product);
        verify(productMapper,times(1)).toProduct(productRequestDTO);
        verify(productMapper,times(1)).toProductResponseDTO(product);
    }

    @Test
    @DisplayName("Product Service Implementation Test - ✅ Should successfully delete product when ID is found")
    void shouldSuccessfullyDeleteProduct() {
        // Arrange
        when(productRepo.findById("75bcb335-f59f-4f27-8a16-39cd95a6fd4e")).thenReturn(Optional.of(product));
        doNothing().when(productRepo).delete(product);
        // Act
        productService.deleteProduct("75bcb335-f59f-4f27-8a16-39cd95a6fd4e");
        // Assert
        // No Assert Just Verifying Delete Method is called or not
        // Verify
        verify(productRepo, times(1)).findById("75bcb335-f59f-4f27-8a16-39cd95a6fd4e");
        verify(productRepo,times(1)).delete(product);
    }


    @Test
    @DisplayName("Product Service Implementation Test - ❌ Should throw exception when trying to delete non-existing product")
    void shouldThrowExceptionWhenDeletingNonExistentProduct() {
        // Arrange
        when(productRepo.findById("Invalid")).thenReturn(Optional.empty());

        // Act
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProduct("Invalid"));
        // Assert

        assertEquals("Product Not Found with Id :Invalid", exception.getMessage());
        // Verify
        verify(productRepo, times(1)).findById("Invalid");
        verify(productRepo, never()).delete(any());
    }

    @Test
    @DisplayName("Product Service Implementation Test - ✅ Should return paginated products")
    void shouldReturnPaginatedProducts() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);

        when(productRepo.findAll(pageable)).thenReturn(productPage);
        when(productMapper.toProductResponseDTO(product)).thenReturn(productResponseDTO);

        // Act
        PaginatedResponse<ProductResponseDTO> response = productService.getPaginatedProducts(0, 10);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertFalse(response.getContent().isEmpty());
        assertEquals(productResponseDTO, response.getContent().getFirst());

        // Verify
        verify(productRepo, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Product Service Implementation Test - ✅ Should return products by category with pagination")
    void shouldReturnProductsByCategoryWithPagination() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);

        when(productRepo.findByProductCategory("Test-Category", pageable)).thenReturn(productPage);
        when(productMapper.toProductResponseDTO(product)).thenReturn(productResponseDTO);

        // Act
        PaginatedResponse<ProductResponseDTO> response = productService.getProductByCategory("Test-Category", 0, 10);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertFalse(response.getContent().isEmpty());
        assertEquals(productResponseDTO, response.getContent().getFirst());

        // Verify
        verify(productRepo, times(1)).findByProductCategory("Test-Category", pageable);
    }


}