package com.nequi.ports.inbound;

import com.nequi.models.Branch;
import reactor.core.publisher.Mono;

public interface BranchServicePort {
    Mono<Branch> createBranch(Branch branch);
    Mono<Void> deleteBranchById(Integer id);
    Mono<Branch> getBranchById(Integer branchId);
}
