package com.nequi.db.repositories;

import com.nequi.db.entities.BranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IBranchRepository extends ReactiveCrudRepository<BranchEntity, Integer> {
    Mono<BranchEntity> findByName(String name);

}
