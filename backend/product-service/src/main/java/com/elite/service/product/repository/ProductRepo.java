package com.elite.service.product.repository;

import com.elite.service.product.document.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepo extends MongoRepository<Product, String>{
    Page<Product> findByProductCategory(String category, Pageable pageable);
}
