package com.nequi.db.mappers;

import com.nequi.db.entities.BranchProductEntity;
import com.nequi.models.BranchProduct;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBranchProductEntityMapper {
    BranchProduct toModel(BranchProductEntity entity);
    BranchProductEntity toEntity(BranchProduct branchProduct);
}
