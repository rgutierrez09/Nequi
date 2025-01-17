package com.nequi.db.repositories;

import com.nequi.db.entities.BranchProductEntity;
import com.nequi.models.BranchProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBranchProductRepository extends ReactiveCrudRepository<BranchProductEntity, Integer> {
    Flux<BranchProductEntity> findByBranchId(Integer branchId);
}
