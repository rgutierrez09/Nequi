package com.nequi.api.dto;

import java.util.List;

public record FranchiseRequestDTO(String name, List<Integer> branchIds ) {
}
