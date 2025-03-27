package com.elite.service.product.mapper;

import com.elite.service.product.document.Product;
import com.elite.service.product.dto.ProductRequestDTO;
import com.elite.service.product.dto.ProductResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductResponseDTO toProductResponseDTO(Product product);
    List<ProductResponseDTO> toProductResponseDTOList(List<Product> products);

    Product toProduct(ProductRequestDTO productRequestDTO);
}
