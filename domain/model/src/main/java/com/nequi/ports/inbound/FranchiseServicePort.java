package com.nequi.ports.inbound;

import com.nequi.models.Franchise;
import reactor.core.publisher.Mono;


public interface FranchiseServicePort {
    Mono<Franchise> createFranchise(Franchise franchise);
    Mono<Void> deleteFranchiseById(Integer id);
}