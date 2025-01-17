package com.nequi.db.mappers;

import com.nequi.db.entities.ProductEntity;
import com.nequi.models.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class IProductEntityMapperImpl implements IProductEntityMapper {

    @Override
    public Product toModel(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( entity.getId() );
        product.setName( entity.getName() );
        product.setStock( entity.getStock() );

        return product;
    }

    @Override
    public ProductEntity toEntity(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId( product.getId() );
        productEntity.setName( product.getName() );
        productEntity.setStock( product.getStock() );

        return productEntity;
    }
}
