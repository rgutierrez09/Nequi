package com.nequi.db.mappers;

import com.nequi.db.entities.FranchiseEntity;
import com.nequi.models.Franchise;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IFranchiseEntityMapper {
    Franchise toModel(FranchiseEntity entity);
    FranchiseEntity toEntity(Franchise franchise);
}
