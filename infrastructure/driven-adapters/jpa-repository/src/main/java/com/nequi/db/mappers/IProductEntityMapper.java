package com.nequi.db.mappers;

import com.nequi.db.entities.ProductEntity;
import com.nequi.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProductEntityMapper {
    Product toModel(ProductEntity entity);
    ProductEntity toEntity(Product product);
}
