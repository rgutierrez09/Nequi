package com.nequi.validations;

import com.nequi.exceptions.BusinessErrorMessage;
import com.nequi.exceptions.BusinessException;
import com.nequi.models.BranchProduct;
import reactor.core.publisher.Mono;

public class BranchProductBusinessValidations {
    public Mono<BranchProduct> validateProductBranch(BranchProduct branchProduct) {
        if (branchProduct.getProductId() == null || branchProduct.getBranchId() == null) {
            return Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_DATA));
        }
        return Mono.just(branchProduct);
    }
}
