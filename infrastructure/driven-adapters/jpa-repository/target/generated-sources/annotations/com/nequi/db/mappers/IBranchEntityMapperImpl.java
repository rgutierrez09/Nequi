package com.nequi.db.mappers;

import com.nequi.db.entities.BranchEntity;
import com.nequi.models.Branch;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-17T14:50:40-0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class IBranchEntityMapperImpl implements IBranchEntityMapper {

    @Override
    public Branch toModel(BranchEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Branch branch = new Branch();

        branch.setId( entity.getId() );
        branch.setName( entity.getName() );

        return branch;
    }

    @Override
    public BranchEntity toEntity(Branch branch) {
        if ( branch == null ) {
            return null;
        }

        BranchEntity branchEntity = new BranchEntity();

        branchEntity.setId( branch.getId() );
        branchEntity.setName( branch.getName() );

        return branchEntity;
    }
}
