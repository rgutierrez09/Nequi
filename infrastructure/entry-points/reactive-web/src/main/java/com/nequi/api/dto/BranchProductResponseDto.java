package com.nequi.api.dto;

import com.nequi.api.utils.AddRouterRestBranchInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AddRouterRestBranchInfo
@NoArgsConstructor
@AllArgsConstructor
public class BranchProductResponseDto {
    private String branchName;
    private List<ProductResponseDto> productResponseDtoList;
}
