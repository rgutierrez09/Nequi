package com.nequi.api.dto;

import com.nequi.api.utils.AddRouterRestBranchInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AddRouterRestBranchInfo
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private String name;
    private int stock;
}
