package com.nequi.usecases;

import com.nequi.ports.inbound.FranchiseServicePort;
import com.nequi.models.Franchise;
import com.nequi.ports.outbound.FranchiseRepositoryPort;
import com.nequi.validations.FranchiseBusinessValidations;
import reactor.core.publisher.Mono;

public class FranchiseUseCase implements FranchiseServicePort {
    private final FranchiseRepositoryPort franchisePersistencePort;
    private final FranchiseBusinessValidations franchiseBusinessValidations;

    public FranchiseUseCase(FranchiseRepositoryPort franchisePersistencePort, FranchiseBusinessValidations franchiseBusinessValidations) {
        this.franchisePersistencePort = franchisePersistencePort;
        this.franchiseBusinessValidations = franchiseBusinessValidations;
    }

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        return franchiseBusinessValidations.validateBranch(franchise)
                .flatMap(franchisePersistencePort::save);
    }

    @Override
    public Mono<Void> deleteFranchiseById(Integer id) {
        return franchisePersistencePort.deleteFranchiseById(id);
    }



}
