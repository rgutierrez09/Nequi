package com.nequi.usecases;

import com.nequi.ports.inbound.FranchiseBranchServicePort;
import com.nequi.models.FranchiseBranch;
import com.nequi.ports.outbound.FranchiseBranchRepositoryPort;
import com.nequi.validations.FranchiseBranchBusinessValidations;
import reactor.core.publisher.Mono;

public class FranchiseBranchUseCase implements FranchiseBranchServicePort {
    private final FranchiseBranchRepositoryPort franchiseBranchPersistencePort;
    private final FranchiseBranchBusinessValidations franchiseBranchBusinessValidations;

    public FranchiseBranchUseCase(FranchiseBranchRepositoryPort franchiseBranchPersistencePort, FranchiseBranchBusinessValidations franchiseBranchBusinessValidations) {
        this.franchiseBranchPersistencePort = franchiseBranchPersistencePort;
        this.franchiseBranchBusinessValidations = franchiseBranchBusinessValidations;
    }

    @Override
    public Mono<Void> associateBranchToFranchise(FranchiseBranch franchiseBranch) {
        return franchiseBranchBusinessValidations.validateFranchiseBranch(franchiseBranch)
                .flatMap(franchiseBranchPersistencePort::saveFranchiseBranch)
                .then();
    }
}
