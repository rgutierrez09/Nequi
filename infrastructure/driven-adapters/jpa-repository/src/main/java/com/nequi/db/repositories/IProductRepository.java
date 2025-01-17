package com.nequi.db.repositories;

import com.nequi.db.entities.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IProductRepository extends ReactiveCrudRepository<ProductEntity, Integer> {
}
