package com.nequi.usecases;

import com.nequi.ports.inbound.BranchServicePort;
import com.nequi.models.Branch;
import com.nequi.ports.outbound.BranchRepositoryPort;
import com.nequi.validations.BranchBusinessValidations;
import reactor.core.publisher.Mono;

public class BranchUseCase implements BranchServicePort {
    private final BranchRepositoryPort branchPersistencePort;
    private final BranchBusinessValidations branchBusinessValidations;

    public BranchUseCase(BranchRepositoryPort branchPersistencePort, BranchBusinessValidations branchBusinessValidations) {
        this.branchPersistencePort = branchPersistencePort;
        this.branchBusinessValidations = branchBusinessValidations;
    }

    @Override
    public Mono<Branch> createBranch(Branch branch) {
        return branchBusinessValidations.validateBranch(branch)
                .flatMap(branchPersistencePort::save);
    }

    @Override
    public Mono<Void> deleteBranchById(Integer id) {
        return branchPersistencePort.deleteBranchById(id);
    }

    @Override
    public Mono<Branch> getBranchById(Integer branchId) {
        return branchPersistencePort.getBranchById(branchId);
    }
}
