package com.nequi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FranchiseBranch {
    private Integer id;
    private Integer franchiseId;
    private Integer branchId;
}
