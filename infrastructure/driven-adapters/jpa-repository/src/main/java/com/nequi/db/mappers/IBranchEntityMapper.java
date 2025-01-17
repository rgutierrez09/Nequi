package com.nequi.db.mappers;

import com.nequi.db.entities.BranchEntity;
import com.nequi.models.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBranchEntityMapper {
    Branch toModel(BranchEntity entity);
    BranchEntity toEntity(Branch branch);
}
