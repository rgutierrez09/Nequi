package com.nequi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private int stock;

    public Product(Integer id, int stock) {
        this.id = id;
        this.stock = stock;
    }
}
