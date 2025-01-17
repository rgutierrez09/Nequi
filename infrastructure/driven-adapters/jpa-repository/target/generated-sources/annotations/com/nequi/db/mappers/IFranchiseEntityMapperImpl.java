package com.nequi.db.mappers;

import com.nequi.db.entities.FranchiseEntity;
import com.nequi.models.Franchise;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-17T14:50:40-0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class IFranchiseEntityMapperImpl implements IFranchiseEntityMapper {

    @Override
    public Franchise toModel(FranchiseEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Franchise franchise = new Franchise();

        franchise.setId( entity.getId() );
        franchise.setName( entity.getName() );

        return franchise;
    }

    @Override
    public FranchiseEntity toEntity(Franchise franchise) {
        if ( franchise == null ) {
            return null;
        }

        FranchiseEntity franchiseEntity = new FranchiseEntity();

        franchiseEntity.setId( franchise.getId() );
        franchiseEntity.setName( franchise.getName() );

        return franchiseEntity;
    }
}
