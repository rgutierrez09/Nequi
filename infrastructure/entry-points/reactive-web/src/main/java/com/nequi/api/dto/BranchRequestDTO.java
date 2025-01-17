package com.nequi.api.dto;

import java.util.List;

public record BranchRequestDTO(String name, List<Integer> productIds) {
}
