package com.nequi.api.mappers;

import com.nequi.api.dto.BranchProductResponseDto;
import com.nequi.api.dto.ProductResponseDto;
import com.nequi.models.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBranchProductResponseDto {
    default BranchProductResponseDto toDto(Branch branch, List<ProductResponseDto> productResponseDtos) {
        BranchProductResponseDto responseDto = new BranchProductResponseDto();
        responseDto.setBranchName(branch.getName());
        responseDto.setProductResponseDtoList(productResponseDtos);
        return responseDto;
    }
}
