package com.nequi.db.repositories;

import com.nequi.db.entities.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IFranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, Integer> {
    Mono<FranchiseEntity> findByName(String name);
}
