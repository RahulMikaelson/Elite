package com.elite.service.product.service;

import com.elite.service.product.document.Product;
import com.elite.service.product.dto.ProductRequestDTO;
import com.elite.service.product.dto.ProductResponseDTO;
import com.elite.service.product.exception.ProductNotFoundException;
import com.elite.service.product.mapper.ProductMapper;
import com.elite.service.product.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<ProductResponseDTO> getAllProducts() {
        log.info("Product Service - Getting All Products");
        return productRepo.findAll().stream().map(productMapper::toProductResponseDTO).collect(Collectors.toList()) ;
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
}
