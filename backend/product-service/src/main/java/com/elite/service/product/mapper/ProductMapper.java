package com.elite.service.product.mapper;

import com.elite.service.product.document.Product;
import com.elite.service.product.dto.ProductRequestDTO;
import com.elite.service.product.dto.ProductResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDTO toProductResponseDTO(Product product);
    List<ProductResponseDTO> toProductResponseDTOList(List<Product> products);
    @Mapping(target = "productId", ignore = true)
    Product toProduct(ProductRequestDTO productRequestDTO);
}
