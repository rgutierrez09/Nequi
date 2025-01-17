package com.nequi.ports.outbound;

import com.nequi.models.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepositoryPort {
    Mono<Franchise> save(Franchise franchise);
    Mono<Void> deleteFranchiseById(Integer id);
}
