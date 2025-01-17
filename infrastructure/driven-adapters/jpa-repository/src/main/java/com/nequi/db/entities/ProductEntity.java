package com.nequi.db.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import static com.nequi.db.constants.Constants.TABLE_PRODUCT_NAME;

@Table(TABLE_PRODUCT_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductEntity {

    @Id
    private Integer id;
    private String name;
    private int stock;
}
