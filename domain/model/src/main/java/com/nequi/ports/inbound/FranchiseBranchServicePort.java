package com.nequi.ports.inbound;

import com.nequi.models.FranchiseBranch;
import reactor.core.publisher.Mono;

public interface FranchiseBranchServicePort {
    Mono<Void> associateBranchToFranchise(FranchiseBranch franchiseBranch);
}
