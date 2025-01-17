package com.nequi.usecases;

import com.nequi.ports.inbound.BranchProductServicePort;
import com.nequi.models.BranchProduct;
import com.nequi.ports.outbound.BranchProductRepositoryPort;
import com.nequi.validations.BranchProductBusinessValidations;
import reactor.core.publisher.Mono;

import java.util.List;

public class BranchProductUseCase implements BranchProductServicePort {
    private final BranchProductRepositoryPort productBranchPersistencePort;
    private final BranchProductBusinessValidations branchProductBusinessValidations;

    public BranchProductUseCase(BranchProductRepositoryPort productBranchPersistencePort, BranchProductBusinessValidations branchProductBusinessValidations) {
        this.productBranchPersistencePort = productBranchPersistencePort;
        this.branchProductBusinessValidations = branchProductBusinessValidations;
    }

    @Override
    public Mono<BranchProduct> associateProductToBranch(BranchProduct branchProduct) {
        return branchProductBusinessValidations.validateProductBranch(branchProduct)
                .flatMap(productBranchPersistencePort::saveProductBranch);
    }

    @Override
    public Mono<List<BranchProduct>> getBranchProductByBranchId(Integer branchId) {
        return productBranchPersistencePort.findBranchProductByBranchId(branchId);
    }

}
