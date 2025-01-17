package com.nequi.api.mappers;

import com.nequi.api.dto.BranchProductCreateRequestDto;
import com.nequi.models.Branch;
import com.nequi.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBranchRequestMapper {
    default Branch toDomain(BranchProductCreateRequestDto branchRequestDTO){
        List<Product> products = mapProductsIds(branchRequestDTO.getProductIds());
        return new Branch(
                null,
                branchRequestDTO.getName(),
                products
        );

    }

    private List<Product> mapProductsIds(List<Integer> products) {
        return products == null ? List.of() : products.stream()
                .map(productId -> new Product(productId, null, 0))
                .collect(Collectors.toList());
    }
}
