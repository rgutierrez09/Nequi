package com.nequi.api.mappers;

import com.nequi.api.dto.ProductResponseDto;
import com.nequi.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProductResponseMapper {
    default ProductResponseDto toProductResponseDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setName(product.getName());
        dto.setStock(product.getStock());
        return dto;
    }
}
