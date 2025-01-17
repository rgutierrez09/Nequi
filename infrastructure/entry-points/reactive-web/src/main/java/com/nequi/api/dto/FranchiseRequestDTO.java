package com.nequi.api.dto;

import com.nequi.models.Product;

import java.util.List;

public record FranchiseRequestDTO(String name, List<Product> products ) {
}
