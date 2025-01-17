package com.nequi.ports.inbound;

import com.nequi.models.BranchProduct;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BranchProductServicePort {
    Mono<BranchProduct> associateProductToBranch(BranchProduct branchProduct);
    Mono<List<BranchProduct>> getBranchProductByBranchId(Integer branchId);
}
