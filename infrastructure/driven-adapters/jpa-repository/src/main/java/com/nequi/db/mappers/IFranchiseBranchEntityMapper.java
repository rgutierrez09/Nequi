package com.nequi.db.mappers;

import com.nequi.db.entities.FranchiseBranchEntity;
import com.nequi.models.FranchiseBranch;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IFranchiseBranchEntityMapper {
    FranchiseBranch toModel(FranchiseBranchEntity entity);
    FranchiseBranchEntity toEntity(FranchiseBranch franchiseBranch);

}
