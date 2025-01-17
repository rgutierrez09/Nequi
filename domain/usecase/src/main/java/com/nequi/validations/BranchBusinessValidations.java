package com.nequi.validations;

import com.nequi.exceptions.BusinessErrorMessage;
import com.nequi.exceptions.BusinessException;
import com.nequi.models.Branch;
import reactor.core.publisher.Mono;

public class BranchBusinessValidations {
    public Mono<Branch> validateBranch(Branch branch) {
        return validateName(branch)
                .then(Mono.just(branch));
    }

    private Mono<Void> validateName(Branch branch) {
        if (branch.getName().isEmpty()) {
            return Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_NAME));
        }
        return Mono.empty(); //TODO COLOCAR EXCEPCION
    }
}
