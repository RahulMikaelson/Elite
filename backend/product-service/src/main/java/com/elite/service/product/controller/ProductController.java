package com.elite.service.product.controller;


import com.elite.service.product.dto.PaginatedResponse;
import com.elite.service.product.dto.ProductRequestDTO;
import com.elite.service.product.dto.ProductResponseDTO;
import com.elite.service.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<ProductResponseDTO>> getPaginatedProducts(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "100") Integer size
    ){
        log.info("Product Controller - Getting all Products for page {} with size {}", page, size);
        return new ResponseEntity<>(productService.getPaginatedProducts(page,size),HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<PaginatedResponse<ProductResponseDTO>> getProductsByCategory(
            @PathVariable("category") String category,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "100") Integer size
    ){
        log.info("Product Controller - Getting all Products for category {}", category);
        return new ResponseEntity<>(productService.getProductByCategory(category,page,size),HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("productId") String productId) {
        log.info("Product Controller - Getting Product by ID {}", productId );
        return new ResponseEntity<>(productService.getProductById(productId),HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        log.info("Product Controller - Adding Product {}", productRequestDTO);
        return new ResponseEntity<>(productService.addProduct(productRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") String productId) {
        log.info("Product Controller - Deleting Product {}", productId);
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
