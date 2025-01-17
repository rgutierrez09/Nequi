package com.nequi.ports.outbound;

import com.nequi.models.BranchProduct;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BranchProductRepositoryPort {
    Mono<BranchProduct> saveProductBranch(BranchProduct branchProduct);
    Mono<List<BranchProduct>>findBranchProductByBranchId(Integer branchId);
}
