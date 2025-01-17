package com.nequi.api.mappers;

import com.nequi.api.dto.FranchiseCreateRequestDTO;
import com.nequi.models.Branch;
import com.nequi.models.Franchise;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IFranchiseRequestMapper {
    default Franchise toDomain(FranchiseCreateRequestDTO franchiseCreateRequestDTO){
        List<Branch> branches = mapBranchesIds(franchiseCreateRequestDTO.getBranchIds());
        return new Franchise(
                null,
                franchiseCreateRequestDTO.getName(),
                branches
        );

    }

    private List<Branch> mapBranchesIds(List<Integer> branches) {
        return branches == null ? List.of() : branches.stream()
                .map(branchId -> new Branch(branchId, null, null))
                .toList();
    }
}
