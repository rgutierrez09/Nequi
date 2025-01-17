package com.nequi.api.dto;

import com.nequi.api.utils.AddRouterRestBranchInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AddRouterRestBranchInfo
@NoArgsConstructor
public class ProductResponseDto {
    private String name;
    private int stock;
}
