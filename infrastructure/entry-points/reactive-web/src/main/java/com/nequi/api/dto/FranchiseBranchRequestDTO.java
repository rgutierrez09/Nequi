package com.nequi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FranchiseBranchRequestDTO {
    private Integer franchiseId;
    private String name;
    private List<Integer> branchIds;
}
