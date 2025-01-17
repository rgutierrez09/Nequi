package com.nequi.ports.outbound;

import com.nequi.models.Branch;
import reactor.core.publisher.Mono;

public interface BranchRepositoryPort {
    Mono<Branch> save(Branch branch);
    Mono<Void> deleteBranchById(Integer id);

    Mono<Branch> getBranchById(Integer branchId);
}
