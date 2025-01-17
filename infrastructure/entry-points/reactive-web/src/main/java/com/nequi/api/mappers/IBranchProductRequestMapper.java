package com.nequi.api.mappers;

import com.nequi.api.dto.BranchProductRequestDTO;
import com.nequi.api.dto.BranchProductUpdateRequestDto;
import com.nequi.models.BranchProduct;
import com.nequi.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBranchProductRequestMapper {
    default List<BranchProduct> toDomain(BranchProductRequestDTO branchProductRequestDTO) {
        return branchProductRequestDTO.getProductIds()
                .stream()
                .map(productId -> new BranchProduct(null, branchProductRequestDTO.getBranchId(), productId))
                .collect(Collectors.toList());
    }
    default List<Product> toProductDomain(BranchProductUpdateRequestDto branchProductUpdateRequestDto) {
        return branchProductUpdateRequestDto.getProductList()
                .stream()
                .map(productDto -> new Product(productDto.getId(), productDto.getStock()))
                .collect(Collectors.toList());
    }
}
