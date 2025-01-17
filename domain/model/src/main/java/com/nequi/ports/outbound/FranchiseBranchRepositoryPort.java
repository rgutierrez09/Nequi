package com.nequi.ports.outbound;

import com.nequi.models.FranchiseBranch;
import reactor.core.publisher.Mono;

public interface FranchiseBranchRepositoryPort {
    Mono<FranchiseBranch> saveFranchiseBranch(FranchiseBranch franchiseBranch);
}
