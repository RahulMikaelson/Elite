package com.elite.service.product.service;

import com.elite.service.product.dto.ProductRequestDTO;
import com.elite.service.product.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(String productId);

    ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO);

    void deleteProduct(String productId);
}
