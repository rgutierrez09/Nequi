package com.nequi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BranchProduct {
    private Integer id;
    private Integer branchId;
    private Integer productId;

}