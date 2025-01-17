package com.nequi.db.mappers;

import com.nequi.db.entities.BranchProductEntity;
import com.nequi.models.BranchProduct;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-17T14:50:39-0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class IBranchProductEntityMapperImpl implements IBranchProductEntityMapper {

    @Override
    public BranchProduct toModel(BranchProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BranchProduct branchProduct = new BranchProduct();

        branchProduct.setId( entity.getId() );
        branchProduct.setBranchId( entity.getBranchId() );
        branchProduct.setProductId( entity.getProductId() );

        return branchProduct;
    }

    @Override
    public BranchProductEntity toEntity(BranchProduct branchProduct) {
        if ( branchProduct == null ) {
            return null;
        }

        BranchProductEntity branchProductEntity = new BranchProductEntity();

        branchProductEntity.setId( branchProduct.getId() );
        branchProductEntity.setBranchId( branchProduct.getBranchId() );
        branchProductEntity.setProductId( branchProduct.getProductId() );

        return branchProductEntity;
    }
}
