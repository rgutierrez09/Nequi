package com.nequi.db.mappers;

import com.nequi.db.entities.FranchiseBranchEntity;
import com.nequi.models.FranchiseBranch;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class IFranchiseBranchEntityMapperImpl implements IFranchiseBranchEntityMapper {

    @Override
    public FranchiseBranch toModel(FranchiseBranchEntity entity) {
        if ( entity == null ) {
            return null;
        }

        FranchiseBranch franchiseBranch = new FranchiseBranch();

        franchiseBranch.setId( entity.getId() );
        franchiseBranch.setFranchiseId( entity.getFranchiseId() );
        franchiseBranch.setBranchId( entity.getBranchId() );

        return franchiseBranch;
    }

    @Override
    public FranchiseBranchEntity toEntity(FranchiseBranch franchiseBranch) {
        if ( franchiseBranch == null ) {
            return null;
        }

        FranchiseBranchEntity franchiseBranchEntity = new FranchiseBranchEntity();

        franchiseBranchEntity.setId( franchiseBranch.getId() );
        franchiseBranchEntity.setFranchiseId( franchiseBranch.getFranchiseId() );
        franchiseBranchEntity.setBranchId( franchiseBranch.getBranchId() );

        return franchiseBranchEntity;
    }
}
