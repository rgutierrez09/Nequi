package com.nequi.api.mappers;

import com.nequi.api.dto.ProductDto;
import com.nequi.api.dto.ProductRequestDTO;
import com.nequi.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProductRequestMapper {

    Product toDomain(ProductRequestDTO productRequestDto);

    default Product toProductDomain(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getStock());
    }

}
