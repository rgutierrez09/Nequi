package com.nequi.api.mappers;

import com.nequi.api.dto.FranchiseBranchRequestDTO;
import com.nequi.models.FranchiseBranch;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IFranchiseBranchRequestMapper {
    default List<FranchiseBranch> toDomain(FranchiseBranchRequestDTO franchiseBranchRequestDTO) {
        return franchiseBranchRequestDTO.getBranchIds()
                .stream()
                .map(branchId -> new FranchiseBranch(null, franchiseBranchRequestDTO.getFranchiseId(), branchId))
                .toList();
    }
}
