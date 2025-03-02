package com.elite.service.product.service;

import com.elite.service.product.dto.PaginatedResponse;
import com.elite.service.product.dto.ProductRequestDTO;
import com.elite.service.product.dto.ProductResponseDTO;

public interface ProductService {

    ProductResponseDTO getProductById(String productId);

    ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO);

    void deleteProduct(String productId);

    PaginatedResponse<ProductResponseDTO> getPaginatedProducts(Integer page, Integer size);

    PaginatedResponse<ProductResponseDTO> getProductByCategory(String category, Integer page, Integer size);
}
