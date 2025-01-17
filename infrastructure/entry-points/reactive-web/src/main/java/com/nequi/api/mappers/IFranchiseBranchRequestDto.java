package com.nequi.api.mappers;

import com.nequi.api.dto.FranchiseBranchRequestDTO;
import com.nequi.models.FranchiseBranch;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IFranchiseBranchRequestDto {
    default List<FranchiseBranch> toDomain(FranchiseBranchRequestDTO franchiseBranchRequestDto){
        return franchiseBranchRequestDto.getBranchIds()
                .stream()
                .map(branchId -> new FranchiseBranch(null, franchiseBranchRequestDto.getFranchiseId(), branchId))
                .toList();
    }
}
