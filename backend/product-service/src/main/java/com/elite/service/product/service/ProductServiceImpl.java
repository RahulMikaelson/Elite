package com.elite.service.product.service;

import com.elite.service.product.document.Product;
import com.elite.service.product.dto.PaginatedResponse;
import com.elite.service.product.dto.ProductRequestDTO;
import com.elite.service.product.dto.ProductResponseDTO;
import com.elite.service.product.exception.ProductNotFoundException;
import com.elite.service.product.mapper.ProductMapper;
import com.elite.service.product.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    private Product getProductByItsId(String productId) {
       return  productRepo.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product Not Found with Id :"+productId,"PRODUCT_NOT_FOUND" ));
    }

    @Override
    public ProductResponseDTO getProductById(String productId) {
        log.info("Product Service - Getting Product by ID {}", productId);
        Product product = getProductByItsId(productId);
        log.info("Product Service - Retrieved Product {}", product);
        return productMapper.toProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toProduct(productRequestDTO);
        log.info("Product Service - Adding Product {}", product);
        Product savedProduct = productRepo.save(product);
        return productMapper.toProductResponseDTO(savedProduct);
    }

    @Override
    public void deleteProduct(String productId) {
        Product productByItsId = getProductByItsId(productId);
        log.info("Product Service - Deleting Product {}", productByItsId);
        productRepo.delete(productByItsId);
    }

    @Override
    public PaginatedResponse<ProductResponseDTO> getPaginatedProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("Product Service - Getting Paginated Products for page {} with size {}", page, size);
        Page<Product> products = productRepo.findAll(pageable);
        List<ProductResponseDTO> productDTOs = products.getContent()
                .stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
        return new PaginatedResponse<>(productDTOs,products.getNumber(),products.getSize(),products.getTotalElements(), products.getTotalPages());

    }

    @Override
    public PaginatedResponse<ProductResponseDTO> getProductByCategory(String category, Integer page, Integer size) {
        log.info("Product Service - Getting Product By Category {}", category);
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepo.findByProductCategory(category, pageable);
        List<ProductResponseDTO> productDTOs = products.getContent()
                .stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
        return new PaginatedResponse<>(productDTOs,products.getNumber(),products.getSize(),products.getTotalElements(), products.getTotalPages());
    }
}
