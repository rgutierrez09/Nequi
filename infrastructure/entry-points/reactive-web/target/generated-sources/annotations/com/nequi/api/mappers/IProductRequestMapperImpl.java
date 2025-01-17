package com.nequi.api.mappers;

import com.nequi.api.dto.ProductRequestDTO;
import com.nequi.models.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class IProductRequestMapperImpl implements IProductRequestMapper {

    @Override
    public Product toDomain(ProductRequestDTO productRequestDto) {
        if ( productRequestDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( productRequestDto.name() );
        product.setStock( productRequestDto.stock() );

        return product;
    }
}
